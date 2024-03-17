package com.example.demo.src.user.model;

import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserAllDetailRes {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNum;
    private boolean isOAuth;
    private String profileImageUrl;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;
    private boolean termsOfUseAgree;
    private boolean termsDataPolicyAgree;
    private boolean termsLocationAgree;
    private UserStatus userStatus;
    private UserGrade userGrade;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    public GetUserAllDetailRes(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNum = user.getPhoneNum();
        this.isOAuth = user.isOAuth();
        this.profileImageUrl = user.getProfileImageUrl();
        this.birthYear = user.getBirthYear();
        this.birthMonth = user.getBirthMonth();
        this.birthDay = user.getBirthDay();
        this.termsOfUseAgree = user.isTermsOfUseAgree();
        this.termsDataPolicyAgree = user.isTermsDataPolicyAgree();
        this.termsLocationAgree = user.isTermsLocationAgree();
        this.userStatus = user.getUserStatus();
        this.userGrade = user.getUserGrade();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.lastLoginAt = user.getLastLoginAt();
    }
}
