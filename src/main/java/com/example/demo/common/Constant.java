package com.example.demo.common;

import java.math.BigDecimal;

public class Constant {
    public final static BigDecimal PRICE = BigDecimal.valueOf(9900);
    public enum SocialLoginType{
        GOOGLE,
        KAKAO,
        NAVER
    }

    public enum UserStatus{
        휴면계정,
        탈퇴신청,
        탈퇴취소,
        탈퇴완료,
        이용정지,
        정상,
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

