package com.example.demo.src.post;

import com.example.demo.common.Constant.LikeStatus;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{

    Like findByPostAndUserAndLikeStatus(Post post, User user, LikeStatus likeStatus);
    boolean existsByPostAndUserAndLikeStatus(Post post, User user, LikeStatus likeStatus);
}
