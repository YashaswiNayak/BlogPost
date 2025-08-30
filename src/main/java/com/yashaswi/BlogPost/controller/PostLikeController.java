package com.yashaswi.BlogPost.controller;

import com.yashaswi.BlogPost.service.PostLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable Integer postId, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isLiked = postLikeService.toggleLike(postId, userDetails.getUsername());
        Long likeCount = postLikeService.getLikeCount(postId);
        Map<String, Object> response = Map.of(
                "liked", isLiked,
                "likeCount", likeCount,
                "message", isLiked ? "Post liked successfully" : "Post unliked successfully"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/likes/count")
    public ResponseEntity<Map<String, Object>> getLikeCount(@PathVariable Integer postId) {
        Long likeCount = postLikeService.getLikeCount(postId);

        Map<String, Object> response = Map.of(
                "postId", postId,
                "likeCount", likeCount
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/likes/status")
    public ResponseEntity<Map<String, Object>> getLikeStatus(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean hasLiked = postLikeService.hasUserLikedPost(postId, userDetails.getUsername());
        Long likeCount = postLikeService.getLikeCount(postId);

        Map<String, Object> response = Map.of(
                "postId", postId,
                "liked", hasLiked,
                "likeCount", likeCount
        );

        return ResponseEntity.ok(response);
    }

}
