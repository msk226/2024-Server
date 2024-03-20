package com.example.demo.src.report;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.annotation.ExistReport;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.model.GetReportRes;
import com.example.demo.src.report.model.PostReportCommentReq;
import com.example.demo.src.report.model.PostReportPostReq;
import com.example.demo.src.report.model.PostReportRes;
import com.example.demo.src.user.UserService;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.POST;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/app/reports")
public class ReportController {

    private final ReportService reportService;
    private final JwtService jwtService;
    private final UserService userService;

    // 게시물 신고
    @ResponseBody
    @PostMapping("/posts")
    @Operation(
        summary = "게시물에 대한 신고 작성 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Request body`에 신고할 내용을 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostReportRes> createReportForPost(@RequestBody @Valid PostReportPostReq postReportPostReq) {
        jwtService.isUserValid(postReportPostReq.getUserId());
        Report report = reportService.createReportForPost(postReportPostReq);
        return new BaseResponse<>(new PostReportRes(report));
    }

    // 댓글 신고
    @ResponseBody
    @PostMapping("/comments")
    @Operation(
        summary = "댓글에 대한 신고 작성 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Request body`에 신고할 내용을 입력하세요."
        + "신고 사유는 SPAM,"
        + "        NUDE_IMAGES,"
        + "        HATE_SPEECH,"
        + "        VIOLENCE_OR_DANGEROUS_ORGANIZATIONS,"
        + "        ILLEGAL_OR_REGULATED_PRODUCTS,"
        + "        HARASSMENT,"
        + "        INTELLECTUAL_PROPERTY_INFRINGEMENT,"
        + "        SUICIDE_OR_SELF_HARM,"
        + "        EATING_DISORDERS,"
        + "        FRAUD_OR_FALSE_INFORMATION,"
        + "        DISLIKED_CONTENT 중 하나 입니다. "
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostReportRes> createReportForComment(@RequestBody @Valid PostReportCommentReq postReportCommentReq) {
        jwtService.isUserValid(postReportCommentReq.getUserId());
        Report report = reportService.createReportForComment(postReportCommentReq);
        return new BaseResponse<>(new PostReportRes(report));
    }

    // 신고 내역 조회 (관리자용)
    @ResponseBody
    @GetMapping("/{reportId}/admin/{adminId}")
    @Operation(
        summary = "# !관리자용! 게시물 신고 조회 API"
        , description = "# 관리자만 이용 가능합니다. `Path Variable`로 조회할 `reportId`를 입력 하세요."
    )
    public BaseResponse<GetReportRes> getReport(@PathVariable @ExistReport Long reportId, @PathVariable @ExistUser Long adminId) {
        userService.isAdmin(adminId);
        Report report = reportService.getReport(reportId, adminId);
        return new BaseResponse<>(new GetReportRes(report));
    }


    // 댓글 및 게시물 신고로 인한 삭제 (관리자용)
    @ResponseBody
    @DeleteMapping("/{reportId}/admin/{adminId}")
    @Operation(
        summary = "# !관리자용! 게시물 및 댓글 신고로 인한 삭제 API"
        , description = "# 관리자만 이용 가능합니다. `Path Variable`로 삭제할 `reportId`를 입력 하세요."
    )
    public BaseResponse<String> deleteReport(@PathVariable @ExistReport Long reportId, @PathVariable @ExistUser Long adminId) {
        userService.isAdmin(adminId);
        reportService.deletePostOrCommentByReport(reportId);
        return new BaseResponse<>("신고 내역 삭제 성공");
    }
}
