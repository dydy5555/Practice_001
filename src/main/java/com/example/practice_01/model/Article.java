package com.example.practice_01.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "article_table")
@Builder
public class Article {
    @Id
    private String id;
    private String title;
    private String description;
    private UserApp user;
}
