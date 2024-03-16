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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        User user = userRepository.findById(postPostingReq.getUserId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        try{
            post.setUser(user);
            postRepository.save(post);
        }catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_POST);
        }
        return new PostPostingRes(post);
    }
    
    public void updatePost(PatchPostingReq patchPostingReq, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        try{
            post.update(patchPostingReq);
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_UPDATE_POST);
        }
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        if (post.getAuthor().getId() != userId){
            throw new BaseException(BaseResponseStatus.NOT_MATCH_USER);
        }
        try{
            post.softDelete();
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_POST);
        }
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

        try{
            if (isAlreadyExistAnswerLike(post, user)) {
                Like like = likeRepository.findByPostAndUser(post, user);
                likeRepository.deleteByPostAndUser(post, user);
                like.cancel();
                return like;
            } else {
                Like like = new Like(user, post);
                return likeRepository.save(like);
            }
        }catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_LIKE);
        }

    }

    public Page<Post> findAllBySearch(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return postRepository.findAllByOrderByCreatedAtDesc(request);
    }


    private boolean isAlreadyExistAnswerLike(Post post, User user){
        return likeRepository.existsByPostAndUser(post, user);
    }



}
