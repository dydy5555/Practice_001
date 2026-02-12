package com.example.practice_01.controller;

import com.example.practice_01.payload.request.UserLogin;
import com.example.practice_01.payload.request.UserRequest;
import com.example.practice_01.payload.response.UserLoginResponse;
import com.example.practice_01.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserLoginResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        UserLoginResponse newUser = authService.registerUser(userRequest);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLogin userLogin){
        UserLoginResponse user = authService.userLogin(userLogin);
        return ResponseEntity.ok(user);
    }


}
