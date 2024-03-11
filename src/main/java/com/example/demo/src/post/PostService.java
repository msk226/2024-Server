package com.example.demo.src.post;


import com.example.demo.common.Constant.LikeStatus;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.model.GetPostingRes;
import com.example.demo.src.post.model.PatchPostingReq;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    // 게시물 작성 로직
    public PostPostingRes createPost(PostPostingReq postPostingReq) {
        Post post = postPostingReq.toEntity();
        post.setUser(userRepository.findById(postPostingReq.getUserId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER)));
        postRepository.save(post);

        return new PostPostingRes(post);
    }
    
    public void updatePost(PatchPostingReq patchPostingReq, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        post.update(patchPostingReq);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        if (post.getAuthor().getId() != userId){
            throw new BaseException(BaseResponseStatus.NOT_MATCH_USER);
        }
        post.delete();
    }
    @Transactional(readOnly = true)
    public GetPostingRes getPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        return new GetPostingRes(post);
    }

    public Like addAndCancelLike(Long postId, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        if (isAlreadyExistAnswerLike(post, user)) {
            Like like = likeRepository.findByPostAndUser(post, user);
            likeRepository.deleteByPostAndUser(post, user);
            like.cancel();
            return like;
        } else {
            Like like = new Like(user, post);
            return likeRepository.save(like);
        }
    }
    private boolean isAlreadyExistAnswerLike(Post post, User user){
        return likeRepository.existsByPostAndUser(post, user);
    }

}
