package com.example.demo.src.user.model;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
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
public class PostUserReq {

    @NotBlank(message = "이메일은 필수 입력사항 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이름은 필수 입력사항 입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력사항 입니다.")
    @Size(min = 10, max = 11, message = "전화번호는 10~11자리 입니다.")
    private String phoneNum;

    private String profileImageUrl;

    @NotNull(message = "생년월일은 필수 입력사항 입니다.")
    @Size(min = 4, max = 4, message = "태어난 년도는 4자리로 입력 해주세요.")
    private Integer birthYear;

    @NotNull(message = "생년월일은 필수 입력사항 입니다.")
    @Size(min = 1, max = 2, message = "태어난 월은 1~2자리 입니다.")
    private Integer birthMonth;

    @NotNull(message = "생년월일은 필수 입력사항 입니다.")
    @Size(min = 1, max = 2, message = "태어난 일은 1~2자리 입니다.")
    private Integer birthDay;

    @NotNull(message = "이용약관 동의는 필수 입력사항 입니다.")
    private boolean termsOfUseAgree;

    @NotNull(message = "개인정보 수집 및 이용 동의는 필수 입력사항 입니다.")
    private boolean termsDataPolicyAgree;

    @NotNull(message = "위치정보 이용 동의는 필수 입력사항 입니다.")
    private boolean termsLocationAgree;

    private UserStatus userStatus;
    private UserGrade userGrade;
    private boolean isOAuth;
    private SocialLoginType socialLoginType;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .isOAuth(this.isOAuth)
                .profileImageUrl(this.profileImageUrl)
                .phoneNum(this.phoneNum)
                .birthYear(this.birthYear)
                .birthMonth(this.birthMonth)
                .birthDay(this.birthDay)
                .termsOfUseAgree(this.termsOfUseAgree)
                .termsDataPolicyAgree(this.termsDataPolicyAgree)
                .termsLocationAgree(this.termsLocationAgree)
                .userGrade(UserGrade.USER)
                .userStatus(UserStatus.정상)
                .lastLoginAt(LocalDateTime.now())
                .consentRenewalDate(LocalDateTime.now())
                .build();
    }
}
