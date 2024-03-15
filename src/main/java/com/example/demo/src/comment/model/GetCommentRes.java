package com.example.demo.src.comment.model;

import com.example.demo.src.comment.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentRes {
    private Long commentId;
    private String content;
    private Long authorId;
    private String authorName;
    private String authorProfileImageUrl;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

public GetCommentRes(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.authorId = comment.getAuthor().getId();
        this.authorName = comment.getAuthor().getName();
        this.authorProfileImageUrl = comment.getAuthor().getProfileImageUrl();
        this.postId = comment.getPost().getId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }


}
