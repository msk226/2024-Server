package com.example.demo.src.report;

import com.example.demo.common.entity.BaseEntity.State;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 신고 하기
    public Report createReportForPost(PostReportPostReq postReportPostReq) {
        User reportUser = userRepository.findById(postReportPostReq.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Post reportPost = postRepository.findById(postReportPostReq.getPostId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        if (reportPost.getState() != State.ACTIVE) {
            throw new BaseException(BaseResponseStatus.POST_DELETED);
        }

        Report report = postReportPostReq.toEntity(reportUser, reportPost);

        try{
            reportPost.addReport(report);
            reportRepository.save(report);
            if (reportRepository.countByReportPostId(reportPost.getId()) >= 5) {
                reportPost.softDelete();
            }
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_POST_REPORT);
        }

        return report;
    }

    public Report createReportForComment(PostReportCommentReq postReportCommentReq) {
        User reportUser = userRepository.findById(postReportCommentReq.getUserId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Comment reportComment = commentRepository.findById(postReportCommentReq.getCommentId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));

        if (reportComment.getState() != State.ACTIVE) {
            throw new BaseException(BaseResponseStatus.COMMENT_DELETED);
        }

        Report report = postReportCommentReq.toEntity(reportUser, reportComment);
        try{
            reportComment.addReport(report);
            reportRepository.save(report);
            if (reportRepository.countByReportCommentId(reportComment.getId()) >= 5) {
                reportComment.softDelete();
            }
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_POST_REPORT);
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

    public void deletePostOrCommentByReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REPORT_NOT_FOUND));

        if (report.getReportComment() != null) {
            Comment comment = report.getReportComment();
            try{
                commentRepository.delete(comment);
                report.softDelete();
            }catch (Exception ignored){
                throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_COMMENT);

        }
        if (report.getReportPost() != null){
            Post post = report.getReportPost();
            try{
                postRepository.delete(post);
                report.softDelete();
            }catch (Exception ignored){
                throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_POST);}
            }
        }
    }
}
