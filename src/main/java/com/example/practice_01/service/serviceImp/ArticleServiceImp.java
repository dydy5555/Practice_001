package com.example.practice_01.service.serviceImp;

import com.example.practice_01.model.Article;
import com.example.practice_01.payload.request.ArticleRequest;
import com.example.practice_01.payload.response.ArticleResponse;
import com.example.practice_01.repository.ArticleRepository;
import com.example.practice_01.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleResponse> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        return  articleList.stream()
                .map(ArticleResponse::toRes)
                .toList();
    }
}
