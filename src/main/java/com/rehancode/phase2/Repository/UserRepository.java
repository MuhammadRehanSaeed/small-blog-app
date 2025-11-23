package com.rehancode.phase2.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.phase2.Entity.User;

@Repository
public interface  UserRepository extends JpaRepository<User,Integer> {

   Optional<User> findByUsername(String username);
}
