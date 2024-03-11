package com.example.demo.src.report.model;

import com.example.demo.common.Constant.ReportReason;
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
}
