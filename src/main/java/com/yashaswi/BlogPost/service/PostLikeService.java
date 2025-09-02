package com.yashaswi.BlogPost.service;

import com.yashaswi.BlogPost.dto.UserResponseDTO;
import com.yashaswi.BlogPost.exception.PostNotFoundException;
import com.yashaswi.BlogPost.mapper.EntityToDtoMapper;
import com.yashaswi.BlogPost.model.Post;
import com.yashaswi.BlogPost.model.PostLike;
import com.yashaswi.BlogPost.model.User;
import com.yashaswi.BlogPost.repository.PostLikeRepository;
import com.yashaswi.BlogPost.repository.PostRepository;
import com.yashaswi.BlogPost.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean toggleLike(Integer postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found " + username));

        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());
        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            return false;//post was unliked
        } else {
            PostLike like = PostLike.builder().post(post).user(user).build();
            postLikeRepository.save(like);
            return true;//post was liked
        }
    }

    public boolean isPostLikedByUser(Integer postId, Integer userId) {
        return postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public Long getLikeCount(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }
        return postLikeRepository.countByPostId(postId);
    }

    public boolean hasUserLikedPost(Integer postId, String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found " + username));
        return isPostLikedByUser(postId, user.getId());
    }

    public Page<UserResponseDTO> getLikeForPost(Integer postId, Pageable pageable) {
        Page<PostLike> likes = postLikeRepository.findByPostId(postId, pageable);
        return likes.map(like -> EntityToDtoMapper.toDto(like.getUser()));
    }
}
