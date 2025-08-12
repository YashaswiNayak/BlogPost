package com.yashaswi.BlogPost.service;

import com.yashaswi.BlogPost.dto.PostDTO;
import com.yashaswi.BlogPost.dto.PostResponseDTO;
import com.yashaswi.BlogPost.exception.PostNotFoundException;
import com.yashaswi.BlogPost.mapper.EntityToDtoMapper;
import com.yashaswi.BlogPost.model.Post;
import com.yashaswi.BlogPost.model.User;
import com.yashaswi.BlogPost.repository.PostRepository;
import com.yashaswi.BlogPost.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDTO createPost(PostDTO ps, String authorUsername) {
        User author = userRepository.findByUserName(authorUsername)
                .orElseThrow(() -> new RuntimeException("Author not found: " + authorUsername));
        Post post = new Post();
        post.setTitle(ps.getTitle());
        post.setPostData(ps.getPostData());
        post.setUser(author);
        Post savedPost = postRepository.save(post);
        return EntityToDtoMapper.toDto(savedPost);
    }

    public PostResponseDTO getPostById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return EntityToDtoMapper.toDto(post);
    }

    public List<PostResponseDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(EntityToDtoMapper::toDto).collect(Collectors.toList());
    }

    public void deletePostById(Integer id, UserDetails userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        boolean isAdmin=userDetails.getAuthorities().stream().anyMatch(auth ->auth.getAuthority().equals("ROLE_ADMIN"));
        if (!post.getUser().getUsername().equals(userDetails.getUsername()) && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Cannot delete others posts");
        }
        postRepository.deleteById(id);
    }

    public PostResponseDTO editPost(Integer id, PostDTO ps,UserDetails userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        boolean isAdmin=userDetails.getAuthorities().stream().anyMatch(auth ->auth.getAuthority().equals("ROLE_ADMIN"));
        if (!post.getUser().getUsername().equals(userDetails.getUsername()) && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Cannot delete others posts");
        }
        post.setTitle(ps.getTitle());
        post.setPostData(ps.getPostData());
        Post updatedPost = postRepository.save(post);
        return EntityToDtoMapper.toDto(updatedPost);
    }
}
