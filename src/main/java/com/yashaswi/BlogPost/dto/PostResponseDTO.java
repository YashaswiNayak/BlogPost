package com.yashaswi.BlogPost.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostResponseDTO {
    private Integer id;
    private String title;
    private String postData;
    private UserResponseDTO author;
    private Integer likeCount;
    private List<CommentResponseDTO> comments;

}