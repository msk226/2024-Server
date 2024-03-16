package com.example.demo.src.report;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.post.PostRepository;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.model.PostReportReq;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 신고하기
    public Report createReport(PostReportReq postReportReq) {
        User reportUser = userRepository.findById(postReportReq.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Post reportPost = postRepository.findById(postReportReq.getPostId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        Report report = postReportReq.toEntity(reportUser, reportPost);
        reportPost.addReport(report);
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    public Report getReport(Long reportId, Long adminId) {
        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        if (!user.isAdmin()) {
            throw new BaseException(BaseResponseStatus.NO_AUTHORITY);
        }
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REPORT_NOT_FOUND));
    }
}
