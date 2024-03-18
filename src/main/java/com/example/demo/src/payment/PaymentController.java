package com.example.demo.src.payment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.payment.model.GetAllSubscribeReq;
import com.example.demo.src.payment.model.GetAllSubscribeRes;
import com.example.demo.src.payment.model.PostPaymentRes;
import com.example.demo.src.payment.model.PostSubscribeRes;
import com.example.demo.utils.JwtService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/app/payments")
public class PaymentController {

    private final JwtService jwtService;
    private final PaymentService paymentService;

    // 결제 진행 후 아래 API 호출 -> 검증 후 통과 하지 못하면 결제 취소. 통과 하면 결제 완료
    @ResponseBody
    @GetMapping("/{impUid}")
    @Operation(
        summary = "결제 완료 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 결제 아이디를 입력 하세요. \n."
        + "결제 진행 후 결제 완료 API를 호출 하세요. 검증 후 통과 하지 못하면 결제 취소, 통과 하면 결제 완료 됩니다."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostPaymentRes> createPayment(@PathVariable Long impUid)
        throws IamportResponseException, IOException {
        PostPaymentRes postPaymentRes = paymentService.createPayment(impUid, jwtService.getUserId());
        return new BaseResponse<>(postPaymentRes);
    }

    // 구독 조회
    @ResponseBody
    @GetMapping("/subscribe/{paymentId}/users/{userId}")
    @Operation(
        summary = "구독 조회 API"
        , description = "# 구독 내역 조회 API 입니다. `Path Variable`로 결제 아이디와 유저 아이디를 입력 하세요."
    )
    public BaseResponse<PostSubscribeRes> getSubscribe(@PathVariable Long paymentId, @PathVariable @ExistUser Long userId) {
        PostSubscribeRes postSubscribeRes = paymentService.findSubscribe(paymentId, userId);
        return new BaseResponse<>(postSubscribeRes);
    }

    // !관리자용! 구독하는 유저 전체 조회
    @ResponseBody
    @GetMapping("/subscribe/admin")
    @Operation(
        summary = "구독하는 유저 전체 조회 API"
        , description = "# !관리자용! 구독하는 유저 전체 조회 API 입니다."
    )
    public BaseResponse<List<GetAllSubscribeRes>> getAllSubscribe(GetAllSubscribeReq getAllSubscribeReq){
        List<GetAllSubscribeRes> getAllSubscribeRes = paymentService.getAllSubscribeForAdmin(getAllSubscribeReq);
        return new BaseResponse<>(getAllSubscribeRes);
    }



    // !관리자용! 구독하는 유저 정보 상세 조회


}
