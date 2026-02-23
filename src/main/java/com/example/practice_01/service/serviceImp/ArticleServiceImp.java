package com.example.practice_01.service.serviceImp;

import com.example.practice_01.exception.NotFoundExceptionClass;
import com.example.practice_01.model.Article;
import com.example.practice_01.model.UserApp;
import com.example.practice_01.payload.request.ArticleRequest;
import com.example.practice_01.payload.response.ArticleResponse;
import com.example.practice_01.payload.response.UserResponse;
import com.example.practice_01.repository.ArticleRepository;
import com.example.practice_01.repository.AuthRepository;
import com.example.practice_01.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp implements ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthRepository authRepository;

    public ArticleServiceImp(ArticleRepository articleRepository, AuthRepository authRepository) {
        this.articleRepository = articleRepository;
        this.authRepository = authRepository;
    }

    @Override
    public List<ArticleResponse> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        return articleList.stream().map(article -> {
            UserApp user = authRepository.findById(article.getCreatorId()).orElse(null);

            return ArticleResponse.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .description(article.getDescription())
                    .creatorId(user.toUserRes())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public ArticleResponse createArticle(ArticleRequest articleRequest) {
        UserApp user = authRepository.findById(articleRequest.getCreatorId())
                .orElseThrow(() -> new NotFoundExceptionClass("User is not found"));
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setCreatorId(user.getId());
        Article savedArticle = articleRepository.save(article);
        return ArticleResponse.toRes(savedArticle,user.toUserRes());

    }
}
