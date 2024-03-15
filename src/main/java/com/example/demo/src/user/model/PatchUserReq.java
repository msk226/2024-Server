package com.example.demo.src.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @Size(min = 10, max = 11, message = "전화번호는 10~11자리 입니다.")
    private String phoneNum;

    private String profileImageUrl;

    @Size(min = 4, max = 4, message = "태어난 년도는 4자리로 입력 해주세요.")
    private Integer birthYear;

    @Size(min = 1, max = 2, message = "태어난 월은 1~2자리 입니다.")
    private Integer birthMonth;

    @Size(min = 1, max = 2, message = "태어난 일은 1~2자리 입니다.")
    private Integer birthDay;

}
