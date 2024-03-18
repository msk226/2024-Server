package com.example.demo.src.user.model;

import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserRes {
    private String nickname;
    private String phoneNum;
    private LocalDateTime createdAt;

    public GetAllUserRes(User user) {
        this.nickname = user.getNickname();
        this.phoneNum = user.getPhoneNum();
        this.createdAt = user.getCreatedAt();
    }
}
