package com.rehancode.phase2.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rehancode.phase2.DTO.CommentDto;
import com.rehancode.phase2.Entity.Comment;
import com.rehancode.phase2.Entity.Post;
import com.rehancode.phase2.Entity.User;
import com.rehancode.phase2.Exception.PostNotFoundException;
import com.rehancode.phase2.Repository.CommentRepository;
import com.rehancode.phase2.Repository.PostRepository;
import com.rehancode.phase2.Repository.UserRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Convert Comment -> DTO
    public CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setContent(comment.getContent());
        dto.setPostId(comment.getPost().getId());
        return dto;
    }

    // Convert DTO -> Comment entity
    public Comment convertToEntity(CommentDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setPost(post);
        return comment;
    }

    // Save comment
    public CommentDto createComment(CommentDto dto) {
        Comment comment = convertToEntity(dto);
        Comment saved = commentRepository.save(comment);
        return convertToDto(saved);
    }

    // Get all comments for a post
public List<CommentDto> getCommentsByPost(int postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));

    // Fetch comments directly from repository
    List<Comment> comments = commentRepository.findByPost(post);

    return comments.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}

}
