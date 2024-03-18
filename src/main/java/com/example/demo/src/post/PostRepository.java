package com.example.demo.src.post;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.src.post.entity.Like;
import com.example.demo.src.post.entity.Post;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findAllByStateOrderByCreatedAtDesc(Pageable pageable, State state);
    Optional<Post> findByIdAndState(Long id, State state);

    List<Post> findAllOrder(Specification<Post> spec, Sort sort);
}
