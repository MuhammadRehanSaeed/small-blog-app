package com.rehancode.phase2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rehancode.phase2.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
