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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Post post = postRepository.findById(postId)
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
    public List<CommentResponseDTO> getAllCommentByPostId(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(EntityToDtoMapper::toDto).collect(Collectors.toList());
    }

    public void deleteComment(Integer commentID) {
        if (!commentRepository.existsById(commentID)) {
            throw new CommentNotFoundException(commentID);
        }
        commentRepository.deleteById(commentID);
    }


}
