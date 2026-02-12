package com.example.practice_01.payload.response;

import com.example.practice_01.common.Provider;
import com.example.practice_01.common.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String id;
    private String fullName;
    private String token;
    private String gmail;
    private String phoneNumber;
    private Role role;
    private Provider provider;
    private String createdDate;
    private String modifiedDate;
    private String loginTime;

}
