package com.example.demo.src.comment.model;

import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.entity.User;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentReq {

    @Size(min = 1, max = 100, message = "댓글은 100자 이내로 작성해주세요.")
    String content;

    @ExistUser
    Long authorId;

    @ExistPost
    Long postId;

    public Comment toEntity(PostCommentReq postCommentReq, User author, Post post) {
        return Comment.builder()
                .content(postCommentReq.getContent())
                .author(author)
                .post(post)
                .build();
    }
}
