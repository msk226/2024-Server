package com.example.demo.src.post;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    // 게시글 삭제

    // 게시글 조회

    // 게시글 좋아요

    // 게시글 좋아요 취소

    // 해시태그로 게시글 조회

    // 게시글 무한 스크롤



}
