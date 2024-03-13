package com.example.demo.src.post.model;


import com.example.demo.src.post.entity.Post;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostingReq {

    @NotBlank(message = "작성자 정보는 필수 입력사항 입니다.")
    Long userId;
    @Size(min = 0, max = 500, message = "게시글은 500자 이내로 작성해주세요.")
    String content;
    @Size(min = 0, max = 10, message = "이미지는 10장 이내로 첨부해주세요.")
    List<String> images;
    List<String> hashTags;

    public Post toEntity() {
        return Post.builder()
            .content(content)
            .images(images)
            .hashTags(hashTags)
            .build();
    }
}
