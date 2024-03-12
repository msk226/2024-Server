package com.example.demo.src.comment.model;

import com.example.demo.src.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRes {

    private Long commentId;
    private LocalDateTime createdAt;

    public PostCommentRes(Comment comment){
        this.commentId = comment.getId();
        this.createdAt = comment.getCreatedAt();
    }
}
