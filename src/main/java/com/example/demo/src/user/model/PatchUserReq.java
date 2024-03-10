package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserReq {
    private String name;
    private String phoneNum;
    private String profileImageUrl;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;

}
