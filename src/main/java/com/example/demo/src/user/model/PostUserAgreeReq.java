package com.example.demo.src.user.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Async.Schedule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserAgreeReq {

    @NotNull(message = "이용약관 동의는 필수 입력사항 입니다.")
    private boolean termsOfUseAgree;
    @NotNull(message = "개인정보 수집 및 이용 동의는 필수 입력사항 입니다.")
    private boolean termsDataPolicyAgree;
    @NotNull(message = "위치정보 이용 동의는 필수 입력사항 입니다.")
    private boolean termsLocationAgree;


}
