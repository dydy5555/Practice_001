package com.example.practice_01.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModify {
    private String phoneNumber;
    private String userProfile;
    private String fullName;
}
