package com.yashaswi.BlogPost.mapper;

import com.yashaswi.BlogPost.dto.CommentResponseDTO;
import com.yashaswi.BlogPost.dto.PostResponseDTO;
import com.yashaswi.BlogPost.dto.UserResponseDTO;
import com.yashaswi.BlogPost.model.Comment;
import com.yashaswi.BlogPost.model.Post;
import com.yashaswi.BlogPost.model.User;

import java.util.Collections;
import java.util.stream.Collectors;

public class EntityToDtoMapper {
    public static CommentResponseDTO toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getComment());
        // FIX: Add a null check before getting the user's details.
        if (comment.getUser() != null) {
            dto.setAuthorUserName(comment.getUser().getUsername());
        }
        return dto;
    }

    public static PostResponseDTO toDto(Post post) {
        if (post == null) {
            return null;
        }
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setPostData(post.getPostData());
        if (post.getUser() != null) {
            dto.setAuthor(toDto(post.getUser()));
        }
        if (post.getLikes() != null) {
            dto.setLikeCount(post.getLikes().size());
        } else {
            dto.setLikeCount(0);
        }

        if (post.getComments() != null) {
            dto.setComments(
                    post.getComments().stream()
                            .map(EntityToDtoMapper::toDto)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setComments(Collections.emptyList());
        }

        return dto;
    }

    public static UserResponseDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUsername());
        dto.setName(user.getName());
        return dto;
    }
}
