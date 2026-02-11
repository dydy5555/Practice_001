package com.example.practice_01.payload.response;

import com.example.practice_01.model.UserApp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String fullName;
//    private String password;
    private String gmail;
    private String phoneNumber;
//    private ZonedDateTime createdDate;
//    private ZonedDateTime modifiedDate;
    public static UserResponse fromEntity(UserApp user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getGmail(),
                user.getPhoneNumber()

        );
    }
}
