package com.example.practice_01.payload.dto;

import com.example.practice_01.common.Provider;
import com.example.practice_01.common.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String fullName;
    private String userProfile;
//    private String username;
    private String gmail;
    private Role role;
    private String phoneNumber;
    private Provider provider;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime loginTime;

}
