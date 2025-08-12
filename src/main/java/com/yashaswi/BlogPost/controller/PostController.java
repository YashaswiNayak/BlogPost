package com.yashaswi.BlogPost.controller;

import com.yashaswi.BlogPost.dto.PostDTO;
import com.yashaswi.BlogPost.dto.PostResponseDTO;
import com.yashaswi.BlogPost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostResponseDTO> displayAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDTO displayPostById(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    //________________________________________________________________________________________
    @PostMapping("/create")
    public PostResponseDTO createNewPost(@RequestBody PostDTO ps, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(ps, userDetails.getUsername());
    }

    //_________________________________________________________________________________________
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePostById(id, userDetails);
        System.out.println("The post with ID: " + id + " has been deleted");
    }

    //_________________________________________________________________________________________
    @PutMapping("/posts/{id}/edit")
    public PostResponseDTO editPostContent(@PathVariable Integer id, @RequestBody PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.editPost(id, postDTO, userDetails);
    }
}

