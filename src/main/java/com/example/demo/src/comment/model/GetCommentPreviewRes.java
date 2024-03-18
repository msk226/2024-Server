package com.example.demo.src.comment.model;

import com.example.demo.src.comment.entity.Comment;
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
public class GetCommentPreviewRes {
    List<GetCommentRes> commentList;
    Integer listSize;
    Integer totalPage;
    Long totalElements;
    boolean isFirst;
    boolean isLast;

    public GetCommentPreviewRes(Page<Comment> comments){
        this.commentList = comments.map(GetCommentRes::new).getContent();
        this.listSize = comments.getNumberOfElements();
        this.totalPage = comments.getTotalPages();
        this.totalElements = comments.getTotalElements();
        this.isFirst = comments.isFirst();
        this.isLast = comments.isLast();
    }

}
