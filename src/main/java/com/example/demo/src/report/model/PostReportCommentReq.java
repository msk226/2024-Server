package com.example.demo.src.report.model;

import com.example.demo.common.Constant.ReportReason;
import com.example.demo.common.validation.annotation.ExistComment;
import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
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
public class PostReportCommentReq {
    @ExistUser
    private Long userId;

    @ExistComment
    private Long commentId;

    private ReportReason reportReason;

    @Size(max = 100, message = "신고 상세 내용은 100자 이하여야 합니다.")
    private String reportDetail;

    public Report toEntity(User user, Comment comment) {
        return Report.builder()
            .reportUser(user)
            .reportPost(null)
            .reportComment(comment)
            .reportDetail(reportDetail)
            .reportReason(reportReason)
            .build();
    }
}
