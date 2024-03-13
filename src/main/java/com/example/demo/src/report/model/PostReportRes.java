package com.example.demo.src.report.model;


import com.example.demo.common.entity.BaseEntity.State;
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
public class PostReportRes {
    private Long reportId;
    private State status;
    private LocalDateTime createdAt;

    public PostReportRes(Report report) {
        this.reportId = report.getId();
        this.status = report.getState();
        this.createdAt = report.getCreatedAt();
    }
}
