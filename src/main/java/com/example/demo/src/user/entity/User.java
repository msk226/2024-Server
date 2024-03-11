package com.example.demo.src.user.entity;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserGrade;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserAgreeReq;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
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
    private Integer birthYear;

    @Column(nullable = false)
    private Integer birthMonth;

    @Column(nullable = false)
    private Integer birthDay;

    @Column(nullable = false)
    private boolean termsOfUseAgree;

    @Column(nullable = false)
    private boolean termsDataPolicyAgree;

    @Column(nullable = false)
    private boolean termsLocationAgree;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrade userGrade;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    private LocalDateTime lastLoginAt;

    private LocalDateTime consentRenewalDate;

    private SocialLoginType socialLoginType;

    public void updateUser(PatchUserReq patchUserReq){
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
    }

}
