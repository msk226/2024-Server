package com.example.demo.src.comment;

import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
