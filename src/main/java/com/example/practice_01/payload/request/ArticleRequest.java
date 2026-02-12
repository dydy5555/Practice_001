package com.example.practice_01.payload.request;

import com.example.practice_01.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRequest {
    private String title;
    private String description;
    public Article toArticleEntity (){
        return Article.builder()
                .id(null)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
