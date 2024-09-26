package com.example.demo.src.comment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.annotation.ExistComment;
import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.comment.model.GetCommentPreviewRes;
import com.example.demo.src.comment.model.GetCommentRes;
import com.example.demo.src.comment.model.PatchCommentReq;
import com.example.demo.src.comment.model.PatchCommentRes;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RestController
@RequestMapping("/app/comment")
public class CommentController {

    private final CommentService commentService;
    private final JwtService jwtService;

    // 댓글 작성
    @ResponseBody
    @PostMapping("")
    @Operation(
        summary = "댓글 작성 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Request Body`로 작성할 댓글의 정보를 입력 하세요. \n."
        + "댓글 작성 성공 시, 댓글의 정보를 반환 합니다."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostCommentRes> createComment(@RequestBody @Valid PostCommentReq postCommentReq){
        jwtService.isUserValid(postCommentReq.getUserId());
        PostCommentRes postCommentRes = commentService.createComment(postCommentReq);
        return new BaseResponse<>(postCommentRes);
    }

    // 특정 댓글 조회 -> 이미 삭제된 댓글은 조회되지 않도록
    @ResponseBody
    @GetMapping("/{commentId}")
    @Operation(
        summary = "댓글 조회 API"
        , description = "# `Path Variable`로 조회할 댓글의 아이디를 입력 하세요. \n."
        + "댓글 조회 성공 시, 댓글의 정보를 반환 합니다. "
    )
    public BaseResponse<GetCommentRes> getComments(@PathVariable @ExistComment Long commentId){
        GetCommentRes getCommentRes = commentService.getComment(commentId);
        return new BaseResponse<>(getCommentRes);
    }


    // 댓글 삭제 -> 이미 삭제된 댓글은 삭제되지 않도록
    @ResponseBody
    @PatchMapping("/{commentId}/author/{authorId}")
    @Operation(
        summary = "댓글 삭제 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 삭제할 댓글의 아이디와 작성자의 아이디를 입력 하세요. \n."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<String> deleteComment(
            @PathVariable @ExistComment Long commentId,
            @PathVariable @ExistUser Long authorId) {
        jwtService.isUserValid(authorId);
        commentService.deleteComment(commentId);
        String message = "댓글이 삭제되었습니다.";
        return new BaseResponse<>(message);
    }


    // 댓글 수정 -> 이미 삭제된 댓글은 수정되지 않도록
    @ResponseBody
    @PatchMapping("/{commentId}")
    @Operation(
        summary = "댓글 수정 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. \n."
        + "`Path Variable`로 수정할 댓글의 아이디를, `Request Body`에는 수정할 댓글의 정보를 입력 하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PatchCommentRes> updateComment(@PathVariable @ExistComment  Long commentId, @RequestBody @Valid PatchCommentReq patchCommentReq) {
        jwtService.isUserValid(patchCommentReq.getAuthorId());
        PatchCommentRes patchCommentRes = commentService.updateComment(commentId, patchCommentReq);
        return new BaseResponse<>(patchCommentRes);
    }

    // 댓글 무한 페이징 조회
    @ResponseBody
    @GetMapping("")
    @Operation(
        summary = "댓글 전체 페이징 조회 API"
        , description = "# 댓글 전체 조회 API 입니다. `Request Param`으로 페이지와 사이즈를 입력하세요."
    )
    public BaseResponse<GetCommentPreviewRes> findCommentByPaging(
        @RequestParam @Min(0) @NotNull(message = "페이지 값은 필수 입력 사항입니다.") Integer page,
        @RequestParam @Min(1) @Max(10) @NotNull(message = "사이즈 값은 필수 입력 사항입니다.") Integer size){
        GetCommentPreviewRes getCommentPreviewRes = commentService.findAllBySearch(page, size);
        return new BaseResponse<>(getCommentPreviewRes);
    }

    // 특정 질문에 대한 댓글 전체 조회 -> 무한 페이징
    @ResponseBody
    @GetMapping("/posts/{postId}")
    @Operation(
        summary = "특정 질문에 대한 댓글 전체 페이징 조회 API"
        , description = "# `Path Variable`로 조회할 질문의 아이디를 입력하세요. `Request Param`으로 페이지와 사이즈를 입력하세요."
    )
    public BaseResponse<GetCommentPreviewRes> findCommentByPostId(
        @PathVariable @ExistPost Long postId,
        @RequestParam @Min(0) @NotNull(message = "페이지 값은 필수 입력 사항입니다.") Integer page,
        @RequestParam @Min(1) @Max(10) @NotNull(message = "사이즈 값은 필수 입력 사항입니다.") Integer size){
        GetCommentPreviewRes getCommentPreviewRes = commentService.findAllBySearchByPostId(postId, page, size);
        return new BaseResponse<>(getCommentPreviewRes);
    }

}
