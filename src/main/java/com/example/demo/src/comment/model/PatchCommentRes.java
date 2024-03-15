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
public class PatchCommentRes {
    private Long commentId;
    private String content;
    private LocalDateTime updatedAt;

    public PatchCommentRes(Comment comment){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.updatedAt = comment.getUpdatedAt();
    }
}
