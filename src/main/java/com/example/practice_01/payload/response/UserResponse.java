package com.example.practice_01.payload.response;

import com.example.practice_01.model.UserApp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String fullName;
    private String username;
    private String userProfile;
    private String gmail;
    private String phoneNumber;
//    private LocalDateTime createdDate;
//    private LocalDateTime modifiedDate;
    public static UserResponse fromEntity(UserApp user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getUserProfile(),
                user.getGmail(),
                user.getPhoneNumber()
//                user.getModifiedDate(),
//                user.getCreatedDate()
        );
    }
}
