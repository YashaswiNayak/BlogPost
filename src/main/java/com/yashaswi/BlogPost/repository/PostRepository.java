package com.yashaswi.BlogPost.repository;

import com.yashaswi.BlogPost.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByDeletedFalse(Pageable pageable);

    Optional<Post> findByIdAndDeletedFalse(Integer id);

    Page<Post> findByUser_UserNameAndDeletedFalse(String username, Pageable pageable);

//    Page<Post> findByPostDataContainingIgnoreCaseAndDeletedFalse(String keyword, Pageable pageable);

//    Page<Post> findByTitleContainingIgnoreCaseAndDeletedFalse(String keyword,Pageable pageable);

    @Query("SELECT p FROM Post p WHERE (LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.postData) LIKE LOWER(CONCAT('%', :query, '%'))) AND p.deleted = false")
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);

}
