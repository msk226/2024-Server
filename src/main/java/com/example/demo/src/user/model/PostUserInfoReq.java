package com.example.demo.src.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserInfoReq {
    @NotBlank(message = "비밀번호는 필수 입력사항 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이름은 필수 입력사항 입니다.")
    @Size(min = 1, max = 20, message = "이름은 20자 이내 입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력사항 입니다.")
    @Size(min = 1, max = 20, message = "닉네임은 20자 이내 입니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수 입력사항 입니다.")
    @Size(min = 10, max = 11, message = "전화번호는 10~11자리 입니다.")
    private String phoneNum;

    @NotNull(message = "생년월일은 필수 입력사항 입니다.")
    @Size(min = 4, max = 4, message = "태어난 년도는 4자리로 입력 해주세요.")
    private String birthYear;

    @NotNull(message = "생년월일은 필수 입력사항 입니다.")
    @Size(min = 1, max = 2, message = "태어난 월은 1~2자리 입니다.")
    private String birthMonth;

    @NotNull(message = "생년월일은 필수 입력사항 입니다.")
    @Size(min = 1, max = 2, message = "태어난 일은 1~2자리 입니다.")
    private String birthDay;

    @NotNull(message = "이용약관 동의는 필수 입력사항 입니다.")
    private boolean termsOfUseAgree;

    @NotNull(message = "개인정보 수집 및 이용 동의는 필수 입력사항 입니다.")
    private boolean termsDataPolicyAgree;

    @NotNull(message = "위치정보 이용 동의는 필수 입력사항 입니다.")
    private boolean termsLocationAgree;

}
