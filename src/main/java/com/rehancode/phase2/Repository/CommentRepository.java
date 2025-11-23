package com.rehancode.phase2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.phase2.Entity.Comment;
import com.rehancode.phase2.Entity.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
 List<Comment> findByPost(Post post);
}
