package com.example.demo.src.post.model;

import com.example.demo.common.Constant.LikeStatus;
import com.example.demo.src.post.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostingLikeRes {
    private Long postId;
    private Long userId;
    private LikeStatus likeStatus;

    public PostPostingLikeRes(Like like) {
        this.postId = like.getPost().getId();
        this.userId = like.getUser().getId();
        this.likeStatus = like.getLikeStatus();
    }
}
