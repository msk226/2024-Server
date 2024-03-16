package com.example.demo.src.comment;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.model.GetCommentPreviewRes;
import com.example.demo.src.comment.model.GetCommentRes;
import com.example.demo.src.comment.model.PatchCommentReq;
import com.example.demo.src.comment.model.PatchCommentRes;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import com.example.demo.src.post.PostRepository;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        User user = userRepository.findByIdAndState(postCommentReq.getAuthorId(), State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        Post post = postRepository.findByIdAndState(postCommentReq.getPostId(), State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        Comment comment = postCommentReq.toEntity(postCommentReq, user, post);
        try{
            commentRepository.save(comment);
            user.addComment(comment); post.addComment(comment);
        }catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_POST_COMMENT);
        }
        return new PostCommentRes(comment);
    }

    public GetCommentRes getComment(Long commentId) {
        Comment comment = commentRepository.findByIdAndState(commentId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        return new GetCommentRes(comment);
    }

    public PatchCommentRes updateComment(Long commentId, PatchCommentReq patchCommentReq) {
        Comment comment = commentRepository.findByIdAndState(commentId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        try{
            comment.update(patchCommentReq.getContent());
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_UPDATE_COMMENT);
        }
        return new PatchCommentRes(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdAndState(commentId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        try{
            comment.softDelete();
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_COMMENT);
        }
    }

    public GetCommentPreviewRes findAllBySearch(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findAllByStateOrderByCreatedAtDesc(request, State.ACTIVE);
        return new GetCommentPreviewRes(comments);
    }

    public GetCommentPreviewRes findAllBySearchByPostId(Long postId, int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findAllByStatePostIdOrderByCreatedAtDesc(postId, request, State.ACTIVE);
        return new GetCommentPreviewRes(comments);
    }
}
