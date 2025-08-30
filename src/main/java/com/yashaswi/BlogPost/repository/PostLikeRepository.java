package com.yashaswi.BlogPost.repository;

import com.yashaswi.BlogPost.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByPostIdAndUserId(Integer postId, Integer userId);

    boolean existsByPostIdAndUserId(Integer postId, Integer userId);

    Long countByPostId(Integer postId);

    void deleteByPostIdAndUserId(Integer postId, Integer userId);
}
