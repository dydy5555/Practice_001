package com.example.practice_01.payload.response;

import com.example.practice_01.common.Provider;
import com.example.practice_01.common.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String id;
    private String fullName;
    private String username;
    private String userProfile;
    private String token;
    private String gmail;
    private String phoneNumber;
    private Role role;
    private Provider provider;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime loginTime;

}
