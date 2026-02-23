package com.example.practice_01.service;


import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.dto.UserDto;
import com.example.practice_01.payload.request.UserLogin;
import com.example.practice_01.payload.request.UserModify;
import com.example.practice_01.payload.request.UserRequest;
import com.example.practice_01.payload.response.UserLoginResponse;
import com.example.practice_01.payload.response.UserResponse;

public interface AuthService {
    UserLoginResponse registerUser(UserRequest userRequest);
    UserApp getUserDetails(String phoneNumber, String provider);

    UserLoginResponse userLogin(UserLogin userLogin);

    UserResponse getUserById(String userId);

    UserDto updateUserInfo(String userId, UserModify userRequest);
}
