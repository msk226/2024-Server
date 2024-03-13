package com.example.demo.src.payment;


import com.example.demo.common.Constant;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.payment.entity.Payments;
import com.example.demo.src.payment.model.PostPaymentRes;
import com.example.demo.src.user.UserRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
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
            payments.softDelete();
            cancelPayment(paymentIamportResponse.getResponse());
            throw new BaseException(BaseResponseStatus.INVALID_PAYMENT);
        }

        Payments payment = Payments.builder()
            .paidAmount(paymentIamportResponse.getResponse().getAmount().longValue())
            .impUid(Long.valueOf(paymentIamportResponse.getResponse().getImpUid()))
            .merchantUid(Long.valueOf(paymentIamportResponse.getResponse().getMerchantUid()))
            .approvedAt(paymentIamportResponse.getResponse().getPaidAt())
            .user(userRepository.findById(userId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER)))
            .build();

        paymentRepository.save(payment);

        return new PostPaymentRes(payment);
    }

    private void cancelPayment(Payment payment) throws IamportResponseException, IOException {
        CancelData cancelData = new CancelData(String.valueOf(payment), true);
        iamportClient.cancelPaymentByImpUid(cancelData);
    }
}
