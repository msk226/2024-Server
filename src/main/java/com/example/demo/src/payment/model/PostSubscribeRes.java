package com.example.demo.src.payment.model;


import com.example.demo.src.payment.entity.Subscribe;
import java.time.LocalDateTime;
import jdk.internal.loader.AbstractClassLoaderValue.Sub;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSubscribeRes {
    Long userId;
    Long paymentId;
    LocalDateTime subscribeAt;

    public PostSubscribeRes(Subscribe subscribe) {
        this.userId = subscribe.getUser().getId();
        this.paymentId = subscribe.getPayment().getId();
        this.subscribeAt = subscribe.getSubscribeAt();
    }
}
