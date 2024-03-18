package com.example.demo.src.user.model;

import com.example.demo.common.Constant.UserStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserReq {
    private String name;
    private String nickname;
    private String phoneNum;
    private UserStatus status;
    private String createdAt;

}
