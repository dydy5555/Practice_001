package com.example.practice_01.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private String id;
    private String title;
    private String description;
    private UserDto user;
}
