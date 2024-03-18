package com.example.demo.src.user;

import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import static com.example.demo.common.entity.BaseEntity.*;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByIdAndState(Long id, State state);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndState(String email, State state);
    List<User> findAllByEmailAndState(String email, State state);
    boolean existsByEmailAndState(String email, State state);
    List<User> findAllByState(State state);


}
