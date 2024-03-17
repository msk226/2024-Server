package com.example.demo.src.report;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.comment.CommentRepository;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.PostRepository;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.model.PostReportCommentReq;
import com.example.demo.src.report.model.PostReportPostReq;
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
    private final CommentRepository commentRepository;

    // 신고하기
    public Report createReportForPost(PostReportPostReq postReportPostReq) {
        User reportUser = userRepository.findById(postReportPostReq.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Post reportPost = postRepository.findById(postReportPostReq.getPostId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        Report report = postReportPostReq.toEntity(reportUser, reportPost);
        try{
            reportPost.addReport(report);
            reportRepository.save(report);
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_POST_REPORT);
        }

        if (reportPost.getReports().size() >= 5) {
            reportPost.softDelete();
            throw new BaseException(BaseResponseStatus.POST_DELETED_BY_REPORT);
        }
        return report;
    }

    public Report createReportForComment(PostReportCommentReq postReportCommentReq) {
        User reportUser = userRepository.findById(postReportCommentReq.getUserId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Comment reportComment = commentRepository.findById(postReportCommentReq.getCommentId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));

        Report report = postReportCommentReq.toEntity(reportUser, reportComment);
        try{
            reportComment.addReport(report);
            reportRepository.save(report);
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_POST_REPORT);
        }

        if (reportComment.getReports().size() >= 5) {
            report.softDelete();
            throw new BaseException(BaseResponseStatus.COMMENT_DELETED_BY_REPORT);
        }
        return report;
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
