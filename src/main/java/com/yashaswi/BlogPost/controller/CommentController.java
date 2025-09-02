package com.yashaswi.BlogPost.controller;

import com.yashaswi.BlogPost.dto.CommentDTO;
import com.yashaswi.BlogPost.dto.CommentResponseDTO;
import com.yashaswi.BlogPost.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResponseDTO> getCommentsForPost(@PathVariable Integer postId, Pageable pageable) {
        return commentService.getAllCommentByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDTO createCommentForPost(
            @PathVariable Integer postId,
            @Valid @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(postId, commentDTO, userDetails.getUsername());
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer commentId, @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails);
    }
}
