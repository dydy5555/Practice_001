package com.example.practice_01.payload.request;

import com.example.practice_01.common.Provider;
import com.example.practice_01.common.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String fullName;
    private String username;
    private String userProfile;
    private String gmail;
    private String phoneNumber;
    private Role role;
    private Provider provider;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String createdDate;
    private String modifiedDate;

}
