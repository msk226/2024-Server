package com.example.demo.src.user.model;

import com.example.demo.src.user.entity.User;
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
                .build();
    }
}
