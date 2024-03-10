package com.example.demo.src.post;


import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 게시물 작성 로직
    public PostPostingRes createPost(PostPostingReq postPostingReq) {
        Post post = postPostingReq.toEntity();
        postRepository.save(post);
        return new PostPostingRes(post);
    }
}
