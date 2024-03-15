package com.example.demo.src.user.entity;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.payment.entity.Subscribe;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserAgreeReq;
import com.example.demo.src.user.model.PostUserInfoReq;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Audited
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "USER") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class User extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20)
    private String phoneNum;

    @Column(nullable = false)
    private boolean isOAuth;

    @Column(length = 100)
    private String profileImageUrl;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer birthYear;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer birthMonth;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer birthDay;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean termsOfUseAgree;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean termsDataPolicyAgree;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean termsLocationAgree;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrade userGrade;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "reportUser", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();

    private LocalDateTime lastLoginAt;

    private LocalDateTime consentRenewalDate;

    @Enumerated(EnumType.STRING)
    private SocialLoginType socialLoginType;


    public void updateUserInfo(PostUserInfoReq postUserInfoReq){
        if (postUserInfoReq == null) {
            throw new BaseException(BaseResponseStatus.INVALID_POST_USER_INFO_REQ);
        }
        this.password = postUserInfoReq.getPassword();
        this.name = postUserInfoReq.getName();
        this.phoneNum = postUserInfoReq.getPhoneNum();
        this.birthYear = postUserInfoReq.getBirthYear();
        this.birthMonth = postUserInfoReq.getBirthMonth();
        this.birthDay = postUserInfoReq.getBirthDay();
        this.termsOfUseAgree = postUserInfoReq.isTermsOfUseAgree();
        this.termsDataPolicyAgree = postUserInfoReq.isTermsDataPolicyAgree();
        this.termsLocationAgree = postUserInfoReq.isTermsLocationAgree();
    }
    public void updateUser(PatchUserReq patchUserReq){
        if (patchUserReq == null) {
            throw new BaseException(BaseResponseStatus.INVALID_PATCH_USER_REQ);
        }

        if(patchUserReq.getName() != null){
            this.name = patchUserReq.getName();
        }
        if(patchUserReq.getProfileImageUrl() != null){
            this.profileImageUrl = patchUserReq.getProfileImageUrl();
        }
        if(patchUserReq.getPhoneNum() != null){
            this.phoneNum = patchUserReq.getPhoneNum();
        }
        if(patchUserReq.getBirthYear() != null){
            this.birthYear = patchUserReq.getBirthYear();
        }
        if(patchUserReq.getBirthMonth() != null){
            this.birthMonth = patchUserReq.getBirthMonth();
        }
        if(patchUserReq.getBirthDay() != null){
            this.birthDay = patchUserReq.getBirthDay();
        }
    }

    public void updateUserAgree(PostUserAgreeReq postUserAgreeReq){
        this.termsOfUseAgree = postUserAgreeReq.isTermsOfUseAgree();
        this.termsDataPolicyAgree = postUserAgreeReq.isTermsDataPolicyAgree();
        this.termsLocationAgree = postUserAgreeReq.isTermsLocationAgree();
    }

    public void deleteUser() {
        this.state = State.INACTIVE;
        this.userStatus = UserStatus.탈퇴완료;
    }

    public void updateLastLoginAt(){this.lastLoginAt = LocalDateTime.now();}

    public void setSocialLoginType(SocialLoginType socialLoginType) {this.socialLoginType = socialLoginType;}

    public boolean isAdmin() {
        return this.userGrade == UserGrade.ADMIN;
    }

}
