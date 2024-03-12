package com.example.demo.src.comment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.model.GetCommentPreviewRes;
import com.example.demo.src.comment.model.GetCommentReq;
import com.example.demo.src.comment.model.GetCommentRes;
import com.example.demo.src.comment.model.PatchCommentReq;
import com.example.demo.src.comment.model.PatchCommentRes;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.model.GetPostingPreviewRes;
import com.example.demo.utils.JwtService;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtService jwtService;
    // 댓글 작성
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCommentRes> createComment(@RequestBody PostCommentReq postCommentReq){
        jwtService.isUserValid(postCommentReq.getAuthorId());
        PostCommentRes postCommentRes = commentService.createComment(postCommentReq);
        return new BaseResponse<>(postCommentRes);
    }

    // 특정 댓글 조회
    @ResponseBody
    @GetMapping("/{commentId}")
    public BaseResponse<GetCommentRes> getComments(@PathVariable Long commentId){
        GetCommentRes getCommentRes = commentService.getComment(commentId);
        return new BaseResponse<>(getCommentRes);
    }


    // 댓글 삭제
    @ResponseBody
    @PatchMapping("/{commentId}/author/{authorId}")
    public BaseResponse<String> deleteComment(Long commentId, Long authorId) {
        jwtService.isUserValid(authorId);
        commentService.deleteComment(commentId);
        String message = "댓글이 삭제되었습니다.";
        return new BaseResponse<>(message);
    }


    // 댓글 수정
    @ResponseBody
    @PatchMapping("/{commentId}")
    public BaseResponse<PatchCommentRes> updateComment(Long commentId, PatchCommentReq patchCommentReq) {
        jwtService.isUserValid(patchCommentReq.getAuthorId());
        PatchCommentRes patchCommentRes = commentService.updateComment(commentId, patchCommentReq);
        return new BaseResponse<>(patchCommentRes);
    }

    // 댓글 무한 페이징
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetCommentPreviewRes> findCommentByPaging(
        @RequestParam @Min(0) Integer page,
        @RequestParam @Min(1) @Max(10) Integer size){
        GetCommentPreviewRes getCommentPreviewRes = commentService.findAllBySearch(page, size);
        return new BaseResponse<>(getCommentPreviewRes);
    }

    // 특정 질문에 대한 댓글 전체 조회
    @ResponseBody
    @GetMapping("/posts/{postId}")
    public BaseResponse<GetCommentPreviewRes> findCommentByPostId(
        @PathVariable Long postId,
        @RequestParam @Min(0) Integer page,
        @RequestParam @Min(1) @Max(10) Integer size){
        GetCommentPreviewRes getCommentPreviewRes = commentService.findAllBySearchByPostId(postId, page, size);
        return new BaseResponse<>(getCommentPreviewRes);
    }

}
