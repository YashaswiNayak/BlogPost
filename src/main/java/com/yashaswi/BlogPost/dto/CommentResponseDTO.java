package com.yashaswi.BlogPost.dto;

import lombok.Data;

@Data
public class CommentResponseDTO {
    private Integer id;
    private String text;
    private String authorUserName;

}
