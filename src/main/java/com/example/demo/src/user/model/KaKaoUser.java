package com.example.demo.src.user.model;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoUser {
    Long id;
    String connected_at;
    KaKaoPropertiesDTO properties;
    KaKaoAccountDTO kakao_account;


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoAccountDTO {
        Boolean has_email;
        Boolean email_needs_agreement;
        Boolean is_email_valid;
        Boolean is_email_verified;
        String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoPropertiesDTO{
        String nickname;
        String profile_image;
        String thumbnail_image;
    }

    public User toEntity() {
        return User.builder()
                .email(this.kakao_account.email)
                .password("NONE")
                .name(this.properties.nickname)
                .isOAuth(true)
                .profileImageUrl(this.properties.profile_image)
                .userStatus(UserStatus.정상)
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
