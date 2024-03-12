package com.example.demo.src.comment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import com.example.demo.src.post.PostRepository;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostCommentRes createComment(PostCommentReq postCommentReq) {
        User user = userRepository.findById(postCommentReq.getUserId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        Post post = postRepository.findById(postCommentReq.getPostId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        Comment comment = postCommentReq.toEntity(postCommentReq, user, post);
        commentRepository.save(comment);
        return new PostCommentRes(comment);
    }


}
