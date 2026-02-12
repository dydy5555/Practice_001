package com.example.practice_01.repository;

import com.example.practice_01.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article,String> {
}
