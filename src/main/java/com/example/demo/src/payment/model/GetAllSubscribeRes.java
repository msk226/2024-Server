package com.example.demo.src.payment.model;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.payment.entity.Payments;
import com.example.demo.src.payment.entity.Subscribe;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSubscribeRes {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNum;
    private boolean isOAuth;
    private String profileImageUrl;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private boolean termsOfUseAgree;
    private boolean termsDataPolicyAgree;
    private boolean termsLocationAgree;
    private UserStatus userStatus;
    private UserGrade userGrade;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime consentRenewalDate;
    private List<Like> likes;
    private List<Report> reports;
    private List<Post> posts;
    private List<Comment> comments;
    private List<Subscribe> subscribes;
    private List<Payments> payments;
    private SocialLoginType socialLoginType;

    public GetAllSubscribeRes(Subscribe subscribe){
        this.id = subscribe.getUser().getId();
        this.email = subscribe.getUser().getEmail();
        this.name = subscribe.getUser().getName();
        this.nickname = subscribe.getUser().getNickname();
        this.phoneNum = subscribe.getUser().getPhoneNum();
        this.isOAuth = subscribe.getUser().isOAuth();
        this.profileImageUrl = subscribe.getUser().getProfileImageUrl();
        this.birthYear = subscribe.getUser().getBirthYear();
        this.birthMonth = subscribe.getUser().getBirthMonth();
        this.birthDay = subscribe.getUser().getBirthDay();
        this.termsOfUseAgree = subscribe.getUser().isTermsOfUseAgree();
        this.termsDataPolicyAgree = subscribe.getUser().isTermsDataPolicyAgree();
        this.termsLocationAgree = subscribe.getUser().isTermsLocationAgree();
        this.userStatus = subscribe.getUser().getUserStatus();
        this.userGrade = subscribe.getUser().getUserGrade();
        this.createdAt = subscribe.getUser().getCreatedAt();
        this.updatedAt = subscribe.getUser().getUpdatedAt();
        this.lastLoginAt = subscribe.getUser().getLastLoginAt();
        this.consentRenewalDate = subscribe.getUser().getConsentRenewalDate();
        this.likes = subscribe.getUser().getLikes();
        this.reports = subscribe.getUser().getReports();
        this.posts = subscribe.getUser().getPosts();
        this.comments = subscribe.getUser().getComments();
        this.subscribes = subscribe.getUser().getSubscribes();
        this.payments = subscribe.getUser().getPayments();
        this.socialLoginType = subscribe.getUser().getSocialLoginType();

    }
}
