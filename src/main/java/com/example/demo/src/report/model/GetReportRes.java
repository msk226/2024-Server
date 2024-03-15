package com.example.demo.src.report.model;

import com.example.demo.common.Constant.ReportReason;
import com.example.demo.src.post.model.GetPostingRes;
import com.example.demo.src.report.entity.Report;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReportRes {
    private Long reportId;
    private Long userId;
    private Long postId;
    private ReportReason reportReason;
    private String reportDetail;
    private LocalDateTime createdAt;

    public GetReportRes(Report report) {
        this.reportId = report.getId();
        this.userId = report.getReportUser().getId();
        this.postId = report.getReportPost().getId();
        this.createdAt = report.getCreatedAt();
        this.reportReason = report.getReportReason();
        this.reportDetail = report.getReportDetail();
    }
}
