package com.rehancode.phase2.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rehancode.phase2.DTO.PostDto;
import com.rehancode.phase2.Entity.Post;
import com.rehancode.phase2.Entity.User;
import com.rehancode.phase2.Exception.PostNotFoundException;
import com.rehancode.phase2.Exception.UsernameNotFoundException;
import com.rehancode.phase2.Repository.PostRepository;
import com.rehancode.phase2.Repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Convert Post entity -> DTO
    public PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUserId(post.getUser().getId());
        return dto;
    }

    // Convert DTO -> Post entity (associate user)
    public Post convertToEntity(PostDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUser(user);
        return post;
    }

    // Create Post
    public PostDto createPost(PostDto dto) {
        Post post = convertToEntity(dto);
        Post saved = postRepository.save(post);
        return convertToDto(saved);
    }

    // Get single post
    public PostDto getPostById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        return convertToDto(post);
    }

    // Get all posts
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Update post
    public PostDto updatePost(int id, PostDto dto) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());

        Post updated = postRepository.save(existing);
        return convertToDto(updated);
    }

    // Delete post
    public void deletePost(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        postRepository.delete(post);
    }
}
