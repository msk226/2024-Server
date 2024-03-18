package com.example.demo.src.post.model;

import com.example.demo.src.post.entity.Post;
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
public class GetPostingRes {

        private Long postId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<String> images;
        private Integer likeCount;
        private Integer commentCount;

        public GetPostingRes(Post post) {
            this.postId = post.getId();
            this.content = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
            this.images = post.getImages();
            this.likeCount = post.getLikes().size();
            this.commentCount = post.getComments().size();
        }

}
