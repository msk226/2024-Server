package com.example.demo.src.comment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.model.GetCommentReq;
import com.example.demo.src.comment.model.GetCommentRes;
import com.example.demo.src.comment.model.PatchCommentReq;
import com.example.demo.src.comment.model.PatchCommentRes;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCommentRes> createComment(@RequestBody PostCommentReq postCommentReq){
        PostCommentRes postCommentRes = commentService.createComment(postCommentReq);
        return new BaseResponse<>(postCommentRes);
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
