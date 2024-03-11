package com.example.demo.src.report;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.report.model.PostReportReq;
import com.example.demo.src.report.model.PostReportRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/reports")
public class ReportController {

    // 게시물 신고
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostReportRes> createReport(@RequestBody PostReportReq postReportReq) {
        return null;
    }


    // 게시물 신고 조회 (관리자용)
}
