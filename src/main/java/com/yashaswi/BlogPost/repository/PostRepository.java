package com.yashaswi.BlogPost.repository;

import com.yashaswi.BlogPost.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
