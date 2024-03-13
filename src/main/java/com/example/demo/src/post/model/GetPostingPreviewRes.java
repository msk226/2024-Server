package com.example.demo.src.post.model;

import com.example.demo.src.post.entity.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostingPreviewRes {

    List<GetPostingRes> postingList;
    Integer listSize;
    Integer totalPage;
    Long totalElements;
    boolean isFirst;
    boolean isLast;

    public GetPostingPreviewRes(Page<Post> posts) {
        this.postingList = posts.map(GetPostingRes::new).getContent();
        this.listSize = posts.getNumberOfElements();
        this.totalPage = posts.getTotalPages();
        this.totalElements = posts.getTotalElements();
        this.isFirst = posts.isFirst();
        this.isLast = posts.isLast();
    }
}
