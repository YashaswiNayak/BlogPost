package com.yashaswi.BlogPost.service;

import com.yashaswi.BlogPost.dto.CommentDTO;
import com.yashaswi.BlogPost.dto.CommentResponseDTO;
import com.yashaswi.BlogPost.exception.CommentNotFoundException;
import com.yashaswi.BlogPost.exception.PostNotFoundException;
import com.yashaswi.BlogPost.mapper.EntityToDtoMapper;
import com.yashaswi.BlogPost.model.Comment;
import com.yashaswi.BlogPost.model.Post;
import com.yashaswi.BlogPost.model.User;
import com.yashaswi.BlogPost.repository.CommentRepository;
import com.yashaswi.BlogPost.repository.PostRepository;
import com.yashaswi.BlogPost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentResponseDTO createComment(Integer postId, CommentDTO commentDTO, String commenterUsername) {
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        User commenter = userRepository.findByUserName(commenterUsername)
                .orElseThrow(() -> new RuntimeException("Commenter not found: " + commenterUsername));

        Comment newComment = new Comment();
        newComment.setComment(commentDTO.getText());
        newComment.setPost(post);
        newComment.setUser(commenter); // Set the author

        Comment savedComment = commentRepository.save(newComment);
        return EntityToDtoMapper.toDto(savedComment);
    }

    //Gets all the comments for a specific post
    public Page<CommentResponseDTO> getAllCommentByPostId(Integer postId, Pageable pageable) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }
        Page<Comment> comments = commentRepository.findByPostId(postId,pageable);
        return comments.map(EntityToDtoMapper::toDto);
    }

    public void deleteComment(Integer commentID, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentID)
                .orElseThrow(() -> new CommentNotFoundException(commentID));

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!comment.getUser().getUsername().equals(userDetails.getUsername()) && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete others' comments");
        }

        commentRepository.delete(comment);

    }
}
