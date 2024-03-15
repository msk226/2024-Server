package com.example.demo.src.post;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.model.GetPostingPreviewRes;
import com.example.demo.src.post.model.GetPostingRes;
import com.example.demo.src.post.model.PatchPostingReq;
import com.example.demo.src.post.model.PostPostingLikeRes;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/app/posts")
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    // 게시글 작성
    @ResponseBody
    @PostMapping("")
    @Operation( security = @SecurityRequirement(name = "jwtToken"))
    public BaseResponse<PostPostingRes> createPost(@RequestBody PostPostingReq postPostingReq) {
        jwtService.isUserValid(postPostingReq.getUserId());
        PostPostingRes postPostingRes = postService.createPost(postPostingReq);
        return new BaseResponse<>(postPostingRes);
    }

    // 게시글 수정
    @ResponseBody
    @PatchMapping("/{postId}")
    @Operation( security = @SecurityRequirement(name = "jwtToken"))
    public BaseResponse<String> updatePost(@RequestBody PatchPostingReq patchPostingReq,
                                           @PathVariable("postId") @ExistPost Long postId){
        jwtService.isUserValid(patchPostingReq.getUserId());
        postService.updatePost(patchPostingReq, postId);
        
        String result = "게시글이 수정되었습니다.";
        return new BaseResponse<>(result);
    }

    // 게시글 삭제

    @ResponseBody
    @PatchMapping("/{postId}/users/{userId}/delete")
    @Operation( security = @SecurityRequirement(name = "jwtToken"))
    public BaseResponse<String> deletePost(
            @PathVariable("postId") @ExistPost Long postId,
            @PathVariable("userId") @ExistUser Long userId){
        jwtService.isUserValid(userId);
        postService.deletePost(postId, userId);
        String result = "게시글이 삭제되었습니다.";
        return new BaseResponse<>(result);
    }

    // 특정 게시글 조회
    @ResponseBody
    @GetMapping("/{postId}")
    @Operation( security = @SecurityRequirement(name = "jwtToken"))
    public BaseResponse<GetPostingRes> getPost(@PathVariable("postId") @ExistPost Long postId){
        GetPostingRes getPostingRes = postService.getPost(postId);
        return new BaseResponse<>(getPostingRes);
    }

    // 게시글 좋아요 기능
    @ResponseBody
    @PostMapping("/{postId}/users/{userId}/like")
    @Operation( security = @SecurityRequirement(name = "jwtToken"))
    public BaseResponse<PostPostingLikeRes> addAndCancelLike(@PathVariable @ExistPost Long postId, @PathVariable @ExistUser Long userId){
        jwtService.isUserValid(userId);
        Like like = postService.addAndCancelLike(postId, userId);
        return new BaseResponse<>(new PostPostingLikeRes(like));
    }

    // 게시글 무한 스크롤
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetPostingPreviewRes> findPostByPaging(
        @RequestParam @Min(0) Integer page,
        @RequestParam @Min(1) @Max(10) Integer size){
        Page<Post> posts = postService.findAllBySearch(page, size);
        return new BaseResponse<>(new GetPostingPreviewRes(posts));
    }
}
