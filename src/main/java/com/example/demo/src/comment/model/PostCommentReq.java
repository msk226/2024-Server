package com.example.demo.src.comment.model;

import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentReq {

    String content;
    Long userId;
    Long postId;

    public Comment toEntity(PostCommentReq postCommentReq, User author, Post post) {
        return Comment.builder()
                .content(postCommentReq.getContent())
                .author(author)
                .post(post)
                .build();
    }
}
