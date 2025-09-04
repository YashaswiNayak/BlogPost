package com.yashaswi.BlogPost.repository;

import com.yashaswi.BlogPost.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByDeletedFalse(Pageable pageable);

    Optional<Post> findByIdAndDeletedFalse(Integer id);

    Page<Post> findByUser_UserNameAndDeletedFalse(String username,Pageable pageable);
}
