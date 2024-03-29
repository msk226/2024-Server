package com.example.demo.src.post;


import com.example.demo.common.Constant.LikeStatus;
import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.specification.EntitySpecification;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.model.GetAllPostingReq;
import com.example.demo.src.post.model.GetAllPostingRes;
import com.example.demo.src.post.model.GetPostingAllDetailRes;
import com.example.demo.src.post.model.GetPostingRes;
import com.example.demo.src.post.model.PatchPostingReq;
import com.example.demo.src.post.model.PostPostingReq;
import com.example.demo.src.post.model.PostPostingRes;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        User user = userRepository.findByIdAndState(postPostingReq.getUserId(), State.ACTIVE)
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
        Post post = postRepository.findByIdAndState(postId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        try{
            post.update(patchPostingReq);
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_UPDATE_POST);
        }
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findByIdAndState(postId, State.ACTIVE)
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
        Post post = postRepository.findByIdAndState(postId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        return new GetPostingRes(post);
    }

    public Like addAndCancelLike(Long postId, Long userId) {
        User user = userRepository.findByIdAndState(userId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        Post post = postRepository.findByIdAndState(postId, State.ACTIVE)
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
        return postRepository.findAllByStateOrderByCreatedAtDesc(request, State.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<GetAllPostingRes> getAllPosts(GetAllPostingReq getAllPostingReq){
        Map<String, Object> search = new HashMap<>();
        if (getAllPostingReq.getNickname() != null){
            search.put("nickname", getAllPostingReq.getNickname());
        }
        if (getAllPostingReq.getCreatedAt() != null) {
            LocalDate date = LocalDate.parse(getAllPostingReq.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime createdAt = date.atTime(0, 0, 0);
            search.put("createdAt", createdAt);
        }
        if (getAllPostingReq.getState() != null) {
            search.put("state", getAllPostingReq.getState());
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "author.createdAt");
        return postRepository.findAll(EntitySpecification.searchPost(search), sort).stream()
            .map(GetAllPostingRes::new)
            .collect(Collectors.toList());
    }

    public void deletePostForAdmin(Long postId) {
        Post post = postRepository.findByIdAndState(postId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        try{
            postRepository.delete(post);
        }
        catch (Exception ignored){
            throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_POST);
        }
    }

    @Transactional(readOnly = true)
    public GetPostingAllDetailRes getPostForAdmin(Long postId) {
        Post post = postRepository.findByIdAndState(postId, State.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        return new GetPostingAllDetailRes(post);
    }



    private boolean isAlreadyExistAnswerLike(Post post, User user){
        return likeRepository.existsByPostAndUser(post, user);
    }



}
