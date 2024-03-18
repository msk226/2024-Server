package com.example.demo.common;

import java.math.BigDecimal;

public class Constant {
    public final static BigDecimal PRICE = BigDecimal.valueOf(9900);
    public enum SocialLoginType{
        NORMAL,
        GOOGLE,
        KAKAO,
        NAVER
    }

    public enum UserStatus {
        DORMANT("휴면 계정"),
        NEEDS_CONSENT("개인정보 동의 필요"),
        WITHDRAWN("탈퇴 완료"),
        SUSPENDED("이용 정지"),
        ACTIVE("정상");

        private final String status;

        UserStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }
    }

    public enum UserGrade{
        USER,
        ADMIN,
    }

    public enum LikeStatus{
        ADD, CANCEL,
    }

    public enum ReportReason{
        SPAM,
        NUDE_IMAGES,
        HATE_SPEECH,
        VIOLENCE_OR_DANGEROUS_ORGANIZATIONS,
        ILLEGAL_OR_REGULATED_PRODUCTS,
        HARASSMENT,
        INTELLECTUAL_PROPERTY_INFRINGEMENT,
        SUICIDE_OR_SELF_HARM,
        EATING_DISORDERS,
        FRAUD_OR_FALSE_INFORMATION,
        DISLIKED_CONTENT
    }

    public enum PaymentState{
        BEFORE_PAYMENT,
        IN_PROGRESS,
        COMPLETED,
        CANCELED

    }


}

