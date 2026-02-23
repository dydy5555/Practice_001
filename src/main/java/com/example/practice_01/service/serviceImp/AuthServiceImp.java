package com.example.practice_01.service.serviceImp;

import com.example.practice_01.common.Provider;
import com.example.practice_01.config.jwt.JwtUtils;
import com.example.practice_01.exception.InvalidExceptionClass;
import com.example.practice_01.exception.NotFoundExceptionClass;
import com.example.practice_01.exception.UserAlreadyExistsException;
import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.dto.UserDto;
import com.example.practice_01.payload.request.UserLogin;
import com.example.practice_01.payload.request.UserModify;
import com.example.practice_01.payload.request.UserRequest;
import com.example.practice_01.payload.response.UserLoginResponse;
import com.example.practice_01.payload.response.UserResponse;
import com.example.practice_01.repository.AuthRepository;
import com.example.practice_01.service.AuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {
    private final AuthRepository authRepository;
    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;
    public AuthServiceImp(AuthRepository authRepository, @Lazy JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional

    public UserLoginResponse registerUser(UserRequest userRequest) {
        if(userRequest.getProvider().equals(Provider.CREDENTIAL)){
            validateUserRegistration(userRequest);
        }
        UserApp saveUser = setUserDetails(userRequest);
        userRequest.setToken(jwtUtils.generateToken(saveUser));
        return  buildUserRegisterResponse(saveUser);
    }

    @Override
    public UserApp getUserDetails(String phoneNumber, String provider) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new RuntimeException("Phone number cannot be null or empty");
        }
        Optional<UserApp> userOptional = authRepository.findByPhoneNumberAndProvider(phoneNumber, Provider.valueOf(provider));
        return userOptional.orElseThrow(() -> new RuntimeException("User not found with the provided Gmail and phone number"));
    }

    @Override
    public UserLoginResponse userLogin(UserLogin request) {
        String username;
        UserApp user;
        if (request.getUsername().contains("@gmail")){
//            username = request.getUsername().startsWith("0") ?
//                    request.getUsername() : "0" + request.getUsername();
            user = authRepository.findByGmailAndProvider(
                            request.getUsername(), Provider.CREDENTIAL)
                    .orElseThrow(() -> new NotFoundExceptionClass("Invalid email"));
        }
//        else if(request.getUsername().startsWith("0")){
//            username = request.getUsername();
//            user = authRepository.findByGmailAndProvider(
//                            phoneNumberOrGmail, Provider.CREDENTIAL)
//                    .orElseThrow(() -> new NotFoundExceptionClass("Invalid phone number or email"));
//        }
        else{
            username = request.getUsername();
            user = authRepository.findByUsernameAndProvider(username,Provider.CREDENTIAL).orElseThrow(()-> new NotFoundExceptionClass("Invalid username"));

        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotFoundExceptionClass("Invalid password");
        }

        return buildUserRegisterResponse(user);
    }

    @Override

    public UserResponse getUserById(String userId) {
        UserApp user = authRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionClass("User not found."));
        return UserResponse.fromEntity(user);
    }

    @Override
    public UserDto updateUserInfo(String userId, UserModify userRequest) {
        UserApp user = authRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionClass("User not found."));
        user.setId(userId);
        user.setUserProfile(userRequest.getUserProfile());
        user.setFullName(userRequest.getFullName());
        authRepository.save(user);
        return user.toDto();
    }

    private UserApp setUserDetails(UserRequest userRequest){
        UserApp user = new UserApp();
        user.setFullName(userRequest.getFullName());
        user.setUsername(userRequest.getUsername());
        user.setUserProfile(userRequest.getUserProfile());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber().startsWith("0") ?
                userRequest.getPhoneNumber() : "0" + userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setGmail(userRequest.getGmail());
        user.setProvider(userRequest.getProvider());
        user.setCreatedDate(LocalDateTime.now());
        user.setModifiedDate(LocalDateTime.now());
        return authRepository.save(user);
    }
    private UserLoginResponse buildUserRegisterResponse(UserApp user) {
        UserLoginResponse response = new UserLoginResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setUserProfile(user.getUserProfile());
        response.setGmail(user.getGmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setProvider(user.getProvider());
        response.setToken(jwtUtils.generateToken(user));
        response.setCreatedDate(user.getCreatedDate());
        response.setModifiedDate(user.getModifiedDate());
        response.setLoginTime(LocalDateTime.now());
        return response;
    }

    private void validateUserRegistration(UserRequest request){
        String getPhoneNumber = request.getPhoneNumber().startsWith("0") ? request.getPhoneNumber() : "0" + request.getPhoneNumber();

        if(authRepository.existsByUsernameAndProvider(request.getUsername(), Provider.CREDENTIAL)){
            throw new UserAlreadyExistsException("A user with this username already exists.");
        }

        if (authRepository.existsByGmailAndProvider(request.getGmail(), Provider.CREDENTIAL)) {
            throw new UserAlreadyExistsException("A user with this email already exists.");
        }

        if (authRepository.existsByPhoneNumberAndProvider(getPhoneNumber, Provider.CREDENTIAL)) {
            throw new UserAlreadyExistsException("A user with this phone number already exists.");
        }

        if(!request.getGmail().contains("@")){
            throw new InvalidExceptionClass("Invalid gmail");
        }
    }
}
