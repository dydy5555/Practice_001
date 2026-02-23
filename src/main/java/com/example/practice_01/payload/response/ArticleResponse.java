package com.example.practice_01.payload.response;

import com.example.practice_01.model.Article;
import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {
    private String id;
    private String title;
    private String description;
    private UserResponse creatorId;

    public static ArticleResponse toRes(Article article, UserResponse user) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .creatorId(user)
                .build();
    }
}
