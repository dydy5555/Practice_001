package com.example.practice_01.payload.dto;

import com.example.practice_01.common.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private Role role;
}
