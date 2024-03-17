package com.example.demo.src.post;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.model.GetAllPostingReq;
import com.example.demo.src.post.model.GetAllPostingRes;
import com.example.demo.src.post.model.GetPostingAllDetailRes;
import com.example.demo.src.post.model.GetPostingPreviewRes;
import com.example.demo.src.post.model.GetPostingRes;
import com.example.demo.src.post.model.PatchPostingReq;
import com.example.demo.src.post.model.PostPostingLikeRes;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import com.example.demo.src.user.UserService;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@RestController
@CrossOrigin
@RequestMapping("/app/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final JwtService jwtService;

    // 게시글 작성
    @ResponseBody
    @PostMapping("")
    @Operation(
        summary = "게시글 작성 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Request body`에 작성할 게시글에 대한 정보를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostPostingRes> createPost(@RequestBody PostPostingReq postPostingReq) {
        jwtService.isUserValid(postPostingReq.getUserId());
        PostPostingRes postPostingRes = postService.createPost(postPostingReq);
        return new BaseResponse<>(postPostingRes);
    }

    // 게시글 수정
    @ResponseBody
    @PatchMapping("/{postId}")
    @Operation(
        summary = "게시글 수정 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Request body`에 수정할 게시글에 대한 정보를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
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
    @Operation(
        summary = "게시글 삭제 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 삭제할 게시글의 `postId`와 `userId`를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
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
    @Operation(
        summary = "특정 게시글 조회 API"
        , description = "# `Path Variable`로 조회할 게시글의 `postId`를 입력하세요.")
    public BaseResponse<GetPostingRes> getPost(@PathVariable("postId") @ExistPost Long postId){
        GetPostingRes getPostingRes = postService.getPost(postId);
        return new BaseResponse<>(getPostingRes);
    }

    // 게시글 좋아요 기능
    @ResponseBody
    @PostMapping("/{postId}/users/{userId}/like")
    @Operation(
        summary = "게시글 좋아요 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 좋아요를 누를 게시글의 `postId`와 `userId`를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostPostingLikeRes> addAndCancelLike(@PathVariable @ExistPost Long postId, @PathVariable @ExistUser Long userId){
        jwtService.isUserValid(userId);
        Like like = postService.addAndCancelLike(postId, userId);
        return new BaseResponse<>(new PostPostingLikeRes(like));
    }

    // 게시글 무한 스크롤
    @ResponseBody
    @GetMapping("")
    @Operation(
        summary = "게시글 무한 스크롤 API"
        , description = "# `Request Param`으로 페이지와 사이즈를 입력하세요."
    )
    public BaseResponse<GetPostingPreviewRes> findPostByPaging(
        @RequestParam @Min(0) @NotNull(message = "페이지 값은 필수 입력 사항입니다.") Integer page,
        @RequestParam @Min(1) @Max(10) @NotNull(message = "사이즈 값은 필수 입력 사항입니다.") Integer size){
        Page<Post> posts = postService.findAllBySearch(page, size);
        return new BaseResponse<>(new GetPostingPreviewRes(posts));
    }

    // !관리자용! 게시글 전체 조회
    @ResponseBody
    @GetMapping("/admin")
    @Operation(
        summary = "# !관리자용! 게시글 전체 조회 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. 또한 관리자만 이용 가능합니다. `Request body`에 게시글 조회에 대한 정보를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<List<GetAllPostingRes>> getAllPosts(@RequestBody GetAllPostingReq getAllPostingReq){
        userService.isAdmin(jwtService.getUserId());
        List<GetAllPostingRes> allPosts = postService.getAllPosts(getAllPostingReq);
        return new BaseResponse<>(allPosts);
    }

    // !관리자용! 게시글 삭제
    @ResponseBody
    @PatchMapping("/admin/{postId}/delete")
    @Operation(
        summary = "# !관리자용! 게시글 삭제 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. 또한 관리자만 이용 가능합니다. `Path Variable`로 삭제할 게시글의 `postId`를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<String> deletePostForAdmin(@PathVariable("postId") @ExistPost Long postId){
        userService.isAdmin(jwtService.getUserId());
        postService.deletePostForAdmin(postId);
        String result = "게시글이 삭제되었습니다.";
        return new BaseResponse<>(result);
    }

    // !관리자용! 게시글 상세 정보 조회
    @ResponseBody
    @GetMapping("/admin/{postId}")
    @Operation(
        summary = "# !관리자용! 게시글 상세 정보 조회 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. 또한 관리자만 이용 가능합니다. `Path Variable`로 조회할 게시글의 `postId`를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<GetPostingAllDetailRes> getPostForAdmin(@PathVariable("postId") @ExistPost Long postId){
        userService.isAdmin(jwtService.getUserId());
        GetPostingAllDetailRes postForAdmin = postService.getPostForAdmin(postId);
        return new BaseResponse<>(postForAdmin);
    }

}
