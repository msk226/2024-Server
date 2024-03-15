package com.example.demo.src.payment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.payment.model.PostPaymentRes;
import com.example.demo.src.payment.model.PostSubscribeRes;
import com.example.demo.utils.JwtService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/payments")
public class PaymentController {

    private final JwtService jwtService;
    private final PaymentService paymentService;

    // 결제 진행 후 아래 API 호출 -> 검증 후 통과 하지 못하면 결제 취소. 통과 하면 결제 완료
    @ResponseBody
    @GetMapping("/{impUid}")
    public BaseResponse<PostPaymentRes> createPayment(@PathVariable Long impUid)
        throws IamportResponseException, IOException {
        PostPaymentRes postPaymentRes = paymentService.createPayment(impUid, jwtService.getUserId());
        return new BaseResponse<>(postPaymentRes);
    }

    // 구독 조회
    @ResponseBody
    @GetMapping("/subscribe/{paymentId}/users/{userId}")
    public BaseResponse<PostSubscribeRes> getSubscribe(@PathVariable Long paymentId, @PathVariable @ExistUser Long userId) {
        PostSubscribeRes postSubscribeRes = paymentService.findSubscribe(paymentId, userId);
        return new BaseResponse<>(postSubscribeRes);
    }



}
