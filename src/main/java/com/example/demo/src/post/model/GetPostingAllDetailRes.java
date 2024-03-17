package com.example.demo.src.post.model;

import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostingAllDetailRes {
    private Long id;
    private String content;
    private List<String> images;
    private Long author;
    private Integer commentsCount;
    private Integer likesCount;

    public GetPostingAllDetailRes(Post post){
        this.id = post.getId();
        this.content = post.getContent();
        this.images = post.getImages();
        this.author = post.getAuthor().getId();
        this.commentsCount = post.getComments().size();
        this.likesCount = post.getLikes().size();
    }

}
