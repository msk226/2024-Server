package com.example.demo.src.comment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.comment.model.GetCommentReq;
import com.example.demo.src.comment.model.GetCommentRes;
import com.example.demo.src.comment.model.PatchCommentReq;
import com.example.demo.src.comment.model.PatchCommentRes;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/comments")
public class CommentController {

    // 댓글 작성
    public BaseResponse<PostCommentRes> createComment(PostCommentReq postCommentReq) {
        return null;
    }

    // 댓글 조회
    public BaseResponse<GetCommentRes> getComments(GetCommentReq getCommentReq) {
        return null;
    }


    // 댓글 삭제
    public BaseResponse<String> deleteComment(Long commentId) {
        return null;
    }


    // 댓글 수정
    public BaseResponse<PatchCommentRes> updateComment(Long commentId, PatchCommentReq patchCommentReq) {
        return null;
    }

    // 댓글 무한 페이징

}
