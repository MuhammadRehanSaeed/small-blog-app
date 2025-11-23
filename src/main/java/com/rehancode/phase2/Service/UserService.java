package com.rehancode.phase2.Service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rehancode.phase2.DTO.AuthResponse;
import com.rehancode.phase2.DTO.LoginRequestDto;
import com.rehancode.phase2.DTO.RegisterRequestDto;
import com.rehancode.phase2.Entity.User;
import com.rehancode.phase2.Exception.BadCredentialsException;
import com.rehancode.phase2.Exception.UsernameNotFoundException;
import com.rehancode.phase2.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    public User converToEntity(RegisterRequestDto dto){
        User user=new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public AuthResponse convertToDTO(User user){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setUsername(user.getUsername());
        authResponse.setEmail(user.getEmail());
        return authResponse;
    }
     public AuthResponse saveUser(RegisterRequestDto dto){
           if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
        throw new BadCredentialsException("Username already exists");
    }
        User user = converToEntity(dto);  
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user); 
        return convertToDTO(savedUser);       
    }

public AuthResponse login(LoginRequestDto dto) {

    User user = userRepo.findByUsername(dto.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("No user found with this username"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        throw new BadCredentialsException("Invalid credentials");
    }

    return convertToDTO(user);
}



}
