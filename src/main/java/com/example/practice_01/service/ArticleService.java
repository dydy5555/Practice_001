package com.example.practice_01.service;

import com.example.practice_01.payload.request.ArticleRequest;
import com.example.practice_01.payload.response.ArticleResponse;

import java.util.List;

public interface ArticleService {
    List<ArticleResponse> getAllArticles();
}
