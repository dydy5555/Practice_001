package com.example.practice_01.model;

import com.example.practice_01.common.Provider;
import com.example.practice_01.common.Role;
import com.example.practice_01.payload.dto.UserDto;
import com.example.practice_01.payload.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_app")
public class UserApp {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String fullName;
    private String username;
    private String password;
    private String userProfile;
    private String gmail;
    private String phoneNumber;
    private Provider provider;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

        public UserDto toDto() {
            return UserDto.builder()
                    .id(this.id)
                    .fullName(this.fullName)
                    .userProfile(this.userProfile)
                    .gmail(this.gmail)
                    .role(this.role)
                    .phoneNumber(this.phoneNumber)
                    .provider(this.provider)
                    .createdDate(this.createdDate)
                    .modifiedDate(this.modifiedDate)
//                    .loginTime(this.loginTime)
                    .build();
        }
        public UserResponse toUserRes (){
            return  UserResponse.builder()
                    .id(this.id)
                    .fullName(this.fullName)
                    .username(this.username)
                    .userProfile(this.userProfile)
                    .gmail(this.gmail)
                    .phoneNumber(this.phoneNumber)
                    .build();

        }
   }
