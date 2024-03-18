package com.example.demo.src.post.model;


import com.example.demo.src.post.entity.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostingRes {

    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public PostPostingRes(Post post) {
        this.postId = post.getId();
        this.userId = post.getAuthor().getId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

}
