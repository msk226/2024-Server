package com.example.demo.src.post;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.post.model.GetPostingRes;
import com.example.demo.src.post.model.PatchPostingReq;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public BaseResponse<PostPostingRes> createPost(@RequestBody PostPostingReq postPostingReq) {
        jwtService.isUserValid(postPostingReq.getUserId());
        PostPostingRes postPostingRes = postService.createPost(postPostingReq);
        return new BaseResponse<>(postPostingRes);
    }

    // 게시글 수정
    @ResponseBody
    @PatchMapping("/{postId}")
    public BaseResponse<String> updatePost(@RequestBody PatchPostingReq patchPostingReq,
                                           @PathVariable("postId") Long postId){
        jwtService.isUserValid(patchPostingReq.getUserId());
        postService.updatePost(patchPostingReq, postId);
        
        String result = "게시글이 수정되었습니다.";
        return new BaseResponse<>(result);
    }

    // 게시글 삭제

    @ResponseBody
    @PatchMapping("/{postId}/delete")
    public BaseResponse<String> deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId, jwtService.getUserId());
        String result = "게시글이 삭제되었습니다.";
        return new BaseResponse<>(result);
    }

    // 특정 게시글 조회
    @ResponseBody
    @GetMapping("/{postId}")
    public BaseResponse<GetPostingRes> getPost(@PathVariable("postId") Long postId){
        GetPostingRes getPostingRes = postService.getPost(postId);
        return new BaseResponse<>(getPostingRes);
    }

    // 게시글 좋아요

    // 게시글 좋아요 취소

    // 해시태그로 게시글 조회

    // 게시글 무한 스크롤



}
