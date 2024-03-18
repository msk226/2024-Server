package com.example.demo.src.comment.model;

import com.example.demo.common.validation.annotation.ExistUser;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchCommentReq {
    @Size(min = 1, max = 100, message = "댓글은 100자 이내로 작성해주세요.")
    private String content;

    @ExistUser
    private Long authorId;


}
