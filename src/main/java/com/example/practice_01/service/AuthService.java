package com.example.practice_01.service;


import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.request.UserLogin;
import com.example.practice_01.payload.request.UserRequest;
import com.example.practice_01.payload.response.UserLoginResponse;
import com.example.practice_01.payload.response.UserResponse;

import java.util.List;

public interface AuthService {
    List<UserResponse> getAllUsers();

    UserLoginResponse registerUser(UserRequest userRequest);
    UserApp getUserDetails(String phoneNumber, String provider);

    UserLoginResponse userLogin(UserLogin userLogin);
}
