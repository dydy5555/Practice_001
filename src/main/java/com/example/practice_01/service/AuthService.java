package com.example.practice_01.service;


import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.request.UserLogin;
import com.example.practice_01.payload.request.UserRequest;
import com.example.practice_01.payload.response.UserLoginResponse;

public interface AuthService {
    UserLoginResponse registerUser(UserRequest userRequest);
    UserApp getUserDetails(String phoneNumber, String provider);

    UserLoginResponse userLogin(UserLogin userLogin);
}
