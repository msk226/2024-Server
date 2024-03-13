package com.example.demo.src.report.model;

import com.example.demo.common.Constant.ReportReason;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReportReq {
    private Long userId;
    private Long postId;
    private ReportReason reportReason;
    private String reportDetail;

    public Report toEntity(User user, Post post) {
        return Report.builder()
                .reportUser(user)
                .reportPost(post)
                .reportDetail(reportDetail)
                .reportReason(reportReason)
                .build();
    }
}
