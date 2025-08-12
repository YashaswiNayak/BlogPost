package com.yashaswi.BlogPost.repository;

import com.yashaswi.BlogPost.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByPostId(Integer postId);
}
