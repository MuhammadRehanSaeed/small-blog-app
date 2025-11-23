package com.rehancode.phase2.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rehancode.phase2.DTO.AuthResponse;
import com.rehancode.phase2.DTO.LoginRequestDto;
import com.rehancode.phase2.DTO.RegisterRequestDto;
import com.rehancode.phase2.Entity.User;
import com.rehancode.phase2.Service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequestDto dto) {
        AuthResponse response= userService.saveUser(dto);
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
     return ResponseEntity.ok(userService.login(dto));
    }
    
    

}
