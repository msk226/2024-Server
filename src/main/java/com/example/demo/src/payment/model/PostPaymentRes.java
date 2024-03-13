package com.example.demo.src.payment.model;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.src.payment.entity.Payments;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPaymentRes {
    Long impUid;
    Long merchantUid;
    Long paidAmount;
    State state;

    public PostPaymentRes(Payments payments) {
        this.impUid = payments.getImpUid();
        this.merchantUid = payments.getMerchantUid();
        this.paidAmount = payments.getPaidAmount();
        this.state = payments.getState();
    }
}
