package com.example.demo.src.user.model;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//구글(서드파티)로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;


    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password("NONE")
                .name(this.name)
                .isOAuth(true)
                .profileImageUrl(this.picture)
                .termsOfUseAgree(false)
                .termsLocationAgree(false)
                .termsDataPolicyAgree(false)
                .userGrade(UserGrade.USER)
                .userStatus(UserStatus.정상)
                .lastLoginAt(LocalDateTime.now())
                .phoneNum("NONE")
                .birthYear(0)
                .birthMonth(0)
                .birthDay(0)
                .socialLoginType(SocialLoginType.GOOGLE)
                .build();
    }
}
