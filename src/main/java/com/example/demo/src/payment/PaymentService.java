package com.example.demo.src.payment;


import com.example.demo.common.Constant;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.specification.EntitySpecification;
import com.example.demo.src.payment.entity.Payments;
import com.example.demo.src.payment.entity.Subscribe;
import com.example.demo.src.payment.model.GetAllSubscribeReq;
import com.example.demo.src.payment.model.GetAllSubscribeRes;
import com.example.demo.src.payment.model.PostPaymentRes;
import com.example.demo.src.payment.model.PostSubscribeRes;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.GetAllUserRes;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {

    @Value("${imp.api-key}")
    private String restApiKey;

    @Value("${imp.secret-key}")
    private String restApiSecret;
    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;

    // 결제 로직
    public PostPaymentRes createPayment(Long impUid, Long userId) throws BaseException, IamportResponseException, IOException {

        IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(String.valueOf(impUid));

        // 이미 존재하는 결제인지 확인
        if (paymentRepository.existsByImpUidAndMerchantUid(impUid, Long.valueOf(paymentIamportResponse.getResponse().getMerchantUid()))){
            throw new BaseException(BaseResponseStatus.ALREADY_EXIST_PAYMENT);
        }

        // 결제 정보 유효성 확인 -> 금액 비교
        if (!Constant.PRICE.equals(paymentIamportResponse.getResponse().getAmount())){
            // 결제 취소
            Payments payments = paymentRepository.findByImpUidAndMerchantUid(impUid, Long.valueOf(paymentIamportResponse.getResponse().getMerchantUid()));
            try{
                payments.softDelete();
                cancelSubscribe(userId, payments.getId());
                cancelPayment(paymentIamportResponse.getResponse());
            }catch (Exception e){
                throw new BaseException(BaseResponseStatus.FAILED_TO_CANCEL_PAYMENT);
            }
            throw new BaseException(BaseResponseStatus.INVALID_PAYMENT);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
        Payments payment = Payments.builder()
            .paidAmount(paymentIamportResponse.getResponse().getAmount().longValue())
            .impUid(Long.valueOf(paymentIamportResponse.getResponse().getImpUid()))
            .merchantUid(Long.valueOf(paymentIamportResponse.getResponse().getMerchantUid()))
            .approvedAt(paymentIamportResponse.getResponse().getPaidAt())
            .user(user)
            .build();


        // 구독 정보가 존재하면, 결제 취소
        if (paymentRepository.existsByImpUidAndMerchantUid(impUid, Long.valueOf(paymentIamportResponse.getResponse().getMerchantUid()))){
            try{
                cancelPayment(paymentIamportResponse.getResponse());
            }
            catch (Exception e){
                throw new BaseException(BaseResponseStatus.FAILED_TO_CANCEL_PAYMENT);
            }
            throw new BaseException(BaseResponseStatus.ALREADY_EXIST_PAYMENT);
        }

        try{
            subscribeRepository.save(createSubscribe(userId, payment.getId()));
            paymentRepository.save(payment);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.FAILED_TO_PAYMENT);
        }

        return new PostPaymentRes(payment);
    }

    // 구독 조회
    public PostSubscribeRes findSubscribe(Long userId, Long paymentId) {
        return new PostSubscribeRes(subscribeRepository.findByUserIdAndPaymentId(userId, paymentId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_SUBSCRIBE)));
    }

    public List<GetAllSubscribeRes> getAllSubscribeForAdmin(GetAllSubscribeReq getAllSubscribeReq){
        Map<String, Object> subscribeDetail = new HashMap<>();
        if (getAllSubscribeReq.getName() != null){
            subscribeDetail.put("name", getAllSubscribeReq.getName());
        }
        if (getAllSubscribeReq.getNickname() != null){
            subscribeDetail.put("nickname", getAllSubscribeReq.getNickname());
        }
        if (getAllSubscribeReq.getPhoneNum() != null){
            subscribeDetail.put("phoneNum", getAllSubscribeReq.getPhoneNum());
        }
        if (getAllSubscribeReq.getStatus() != null){
            subscribeDetail.put("status", getAllSubscribeReq.getStatus());
        }
        if (getAllSubscribeReq.getCreatedAt() != null){
            LocalDate date = LocalDate.parse(getAllSubscribeReq.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime createdAt = date.atTime(0, 0, 0);
            subscribeDetail.put("createdAt", createdAt);
        }
        return subscribeRepository.findAll(EntitySpecification.searchSubscribe(subscribeDetail)).stream()
            .map(GetAllSubscribeRes::new)
            .collect(Collectors.toList());
    }

    public GetAllSubscribeRes getSubscribe(Long subscribeId) {
        return new GetAllSubscribeRes(subscribeRepository.findById(subscribeId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_SUBSCRIBE)));
    }

    // 정기 결제 로직
    public void createRegularPayment() {

    }


    // 결제 취소 로직
    private void cancelPayment(Payment payment) throws IamportResponseException, IOException {
        CancelData cancelData = new CancelData(String.valueOf(payment), true);
        iamportClient.cancelPaymentByImpUid(cancelData);
    }

    // 구독 로직
    private Subscribe createSubscribe(Long userId, Long paymentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
        Payments payment = paymentRepository.findById(paymentId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PAYMENT));
        Subscribe subscribe = Subscribe.builder()
            .user(user)
            .payment(payment)
            .subscribeAt(payment.getCreatedAt())
            .build();
        try{
            user.addSubscribe(subscribe, payment); payment.addSubscribe(subscribe);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.FAILED_TO_SUBSCRIBE);
        }

        return subscribe;
    }

    // 구독 취소 로직
    private void cancelSubscribe(Long userId, Long paymentId) {
        if (!subscribeRepository.existsByUserIdAndPaymentId(userId, paymentId)){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_SUBSCRIBE);
        }
        Subscribe subscribe = subscribeRepository.findByUserIdAndPaymentId(userId, paymentId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PAYMENT));
        try{
            subscribe.softDelete();
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.FAILED_TO_CANCEL_SUBSCRIBE);
        }
    }
}
