package com.yashaswi.BlogPost.controller;

import com.yashaswi.BlogPost.dto.PostDTO;
import com.yashaswi.BlogPost.dto.PostResponseDTO;
import com.yashaswi.BlogPost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostResponseDTO> displayAllPosts(Pageable pageable) {
        return postService.getAllPost(pageable);
    }

    @GetMapping("/{id}")
    public PostResponseDTO displayPostById(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    //________________________________________________________________________________________
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDTO createNewPost(@RequestBody PostDTO ps, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(ps, userDetails.getUsername());
    }

    //_________________________________________________________________________________________
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePostById(id, userDetails);
        System.out.println("The post with ID: " + id + " has been deleted");
    }

    //_________________________________________________________________________________________
    @PutMapping("/{id}")
    public PostResponseDTO editPostContent(@PathVariable Integer id, @RequestBody PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.editPost(id, postDTO, userDetails);
    }
}

