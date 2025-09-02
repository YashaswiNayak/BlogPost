package com.yashaswi.BlogPost.repository;

import com.yashaswi.BlogPost.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findByPostId(Integer postId, Pageable pageable);
}
