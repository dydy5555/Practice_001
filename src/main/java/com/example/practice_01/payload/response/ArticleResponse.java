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
public class ArticleResponse {
    private String id;
    private String title;
    private String description;
    private UserApp user;

    public static ArticleResponse toRes(Article article){
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getUser()

        );
    }
}
