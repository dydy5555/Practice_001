package com.example.practice_01.service.serviceImp;

import com.example.practice_01.common.Provider;
import com.example.practice_01.config.jwt.JwtUtils;
import com.example.practice_01.exception.NotFoundExceptionClass;
import com.example.practice_01.exception.UserAlreadyExistsException;
import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.request.UserLogin;
import com.example.practice_01.payload.request.UserRequest;
import com.example.practice_01.payload.response.UserLoginResponse;
import com.example.practice_01.payload.response.UserResponse;
import com.example.practice_01.repository.AuthRepository;
import com.example.practice_01.service.AuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserApp> users = authRepository.findAll();
        return users.stream().map(UserResponse::fromEntity).collect(Collectors.toList());
    }

    @Transactional

    public UserLoginResponse registerUser(UserRequest userRequest) {
        if(userRequest.getProvider().equals(Provider.CREDENTIAL)){
            validateUserRegistration(userRequest);
        }
        UserApp saveUser = setUserDetails(userRequest);
        System.out.println(saveUser);
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
        String phoneNumberOrGmail;
        UserApp user;
        if (!request.getPhoneNumberOrGmail().contains("@gmail")){
            phoneNumberOrGmail = request.getPhoneNumberOrGmail().startsWith("0") ?
                    request.getPhoneNumberOrGmail() : "0" + request.getPhoneNumberOrGmail();
            user = authRepository.findByPhoneNumberAndProvider(
                            phoneNumberOrGmail, Provider.CREDENTIAL)
                    .orElseThrow(() -> new NotFoundExceptionClass("Invalid phone number or email"));
        }else{
            phoneNumberOrGmail = request.getPhoneNumberOrGmail();
            user = authRepository.findByGmailAndProvider(
                            phoneNumberOrGmail, Provider.CREDENTIAL)
                    .orElseThrow(() -> new NotFoundExceptionClass("Invalid phone number or email"));
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotFoundExceptionClass("Invalid password");
        }

        return buildUserRegisterResponse(user);
    }

    private UserApp setUserDetails(UserRequest userRequest){
        UserApp user = new UserApp();
        user.setFullName(userRequest.getFullName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber().startsWith("0") ?
                userRequest.getPhoneNumber() : "0" + userRequest.getPhoneNumber());
        user.setGmail(userRequest.getGmail());
        user.setProvider(userRequest.getProvider());
        user.setCreatedDate(userRequest.getCreatedDate());
        user.setModifiedDate(userRequest.getModifiedDate());
        return authRepository.save(user);
    }
    private UserLoginResponse buildUserRegisterResponse(UserApp user) {
        UserLoginResponse response = new UserLoginResponse();
//        response.setTelegramVerify(telegramBotRepository.findByChatId(user.getChatId()).isPresent());
        response.setId(user.getId());
//        response.setProfileImage(Optional.ofNullable(user.getProfileImage()).orElse(""));
        response.setFullName(user.getFullName());
        response.setGmail(user.getGmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setProvider(user.getProvider());
        response.setToken(jwtUtils.generateToken(user));
        response.setCreatedDate(user.getCreatedDate());
        response.setModifiedDate(user.getModifiedDate());
        response.setLoginTime(response.getLoginTime());
        return response;
    }

    private void validateUserRegistration(UserRequest request){
        String getPhoneNumber = request.getPhoneNumber().startsWith("0") ? request.getPhoneNumber() : "0" + request.getPhoneNumber();

        if (authRepository.existsByGmailAndProvider(request.getGmail(), Provider.CREDENTIAL)) {
            throw new UserAlreadyExistsException("A user with this email already exists.");
        }

        if (authRepository.existsByPhoneNumberAndProvider(getPhoneNumber, Provider.CREDENTIAL)) {
            throw new UserAlreadyExistsException("A user with this phone number already exists.");
        }
    }
}
