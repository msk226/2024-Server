package com.example.demo.src.post.model;

import com.example.demo.src.post.entity.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostingRes {
    private Long postId;
    private String nickname;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String state;
    private Integer likeCount;
    private Integer commentCount;
    private Integer reportCount;
    private List<String> postImgUrl;

    public GetAllPostingRes(Post post){
        this.postId = post.getId();
        this.nickname = post.getAuthor().getNickname();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
        this.state = post.getState().toString();
        this.likeCount = post.getLikes().size();
        this.commentCount = post.getComments().size();
        this.reportCount = post.getReports().size();
        this.postImgUrl = post.getImages();
    }

}
