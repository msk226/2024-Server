package com.example.demo.src.payment.model;

import com.example.demo.common.Constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSubscribeReq {
    private String name;
    private String nickname;
    private String phoneNum;
    private UserStatus status;
    private String createdAt;
}
