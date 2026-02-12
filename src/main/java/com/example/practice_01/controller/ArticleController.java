package com.example.practice_01.controller;

import com.example.practice_01.payload.dto.ArticleDto;
import com.example.practice_01.payload.request.ArticleRequest;
import com.example.practice_01.payload.response.ApiResponse;
import com.example.practice_01.payload.response.ArticleResponse;
import com.example.practice_01.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
        List<ArticleResponse> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }
}
