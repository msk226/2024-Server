package com.example.demo.src.user.entity;

import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Integer termsOfUseAgree;

    @Column(nullable = false)
    private Integer termsDataPolicyAgree;

    @Column(nullable = false)
    private Integer termsLocationAgree;

    private LocalDateTime lastLoginAt;

    private SocialLoginType socialLoginType;

    @Builder
    public User(Long id, String email, String password, String name, boolean isOAuth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.isOAuth = isOAuth;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void deleteUser() {
        this.state = State.INACTIVE;
    }

}