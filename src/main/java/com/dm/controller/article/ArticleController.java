package com.dm.controller.article;

import com.dm.WarehouseManagerException;
import com.dm.controller.article.dto.ArticleAdditionDto;
import com.dm.model.Article;
import com.dm.repository.ArticleRepository;
import com.dm.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private ArticleService articleService;
    private ArticleRepository articleRepository;

    public ArticleController(ArticleService articleService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleAdditionDto> addArticles(@RequestBody ArticleAdditionDto articleAdditionDto) {

        final ArticleAdditionDto inventoryNewState = articleService.addInventory(articleAdditionDto);

        return new ResponseEntity<>(inventoryNewState, HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new WarehouseManagerException("Article with id: " + id + " not found"));
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
