package com.example.demo.src.comment;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndState(Long id, State state);
    Page<Comment> findAllByStateOrderByCreatedAtDesc(Pageable pageable, State state);

    Page<Comment> findAllByStateAndPostIdOrderByCreatedAtDesc(State state, Long post_id,
        Pageable pageable);
}