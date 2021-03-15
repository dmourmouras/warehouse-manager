package com.dm.service;

import com.dm.WarehouseManagerException;
import com.dm.controller.article.dto.ArticleAdditionDto;
import com.dm.controller.article.dto.ArticleDto;
import com.dm.model.Article;
import com.dm.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    @DisplayName("addInventory() should throw exception when stock value is not a positive number")
    void addInventoryTest1() {
        final ArticleAdditionDto articleAdditionDto =
                getArticleAdditionDtoStub(getArticleDtoStub( "screw", "-1"));

        Assertions.assertThrows(WarehouseManagerException.class, () -> {
            articleService.addInventory(articleAdditionDto);
        });
    }

    @Test
    @DisplayName("addInventory() should create a new article when given article name does not exist in DB")
    void addInventoryTest2() {
        final ArticleAdditionDto articleAdditionDto =
                getArticleAdditionDtoStub(getArticleDtoStub( "screw", "10"));

        when(articleRepository.findArticleByName(any(String.class))).thenReturn(null);
        when(articleRepository.save(any(Article.class))).thenReturn(new Article(5L, "screw", 10L));

        final ArticleAdditionDto articleAdditionResponseDto = articleService.addInventory(articleAdditionDto);

        assertEquals("10", articleAdditionResponseDto.getInventory().get(0).getStock());
    }

    @Test
    @DisplayName("addInventory() should add stock to previous stock when given article name exists in DB")
    void addInventoryTest3() {
        final ArticleAdditionDto articleAdditionDto =
                getArticleAdditionDtoStub(getArticleDtoStub( "desk", "10"));

        when(articleRepository.findArticleByName(any(String.class))).thenReturn(new Article(5L, "screw", 10L));
        when(articleRepository.save(any(Article.class))).thenReturn(new Article(5L, "screw", 20L));

        final ArticleAdditionDto articleAdditionResponseDto = articleService.addInventory(articleAdditionDto);

        assertEquals("20", articleAdditionResponseDto.getInventory().get(0).getStock());
    }

    private ArticleDto getArticleDtoStub(String name, String stock) {
        return new ArticleDto(name, stock);
    }

    private ArticleAdditionDto getArticleAdditionDtoStub(ArticleDto articleDto) {
        final ArticleAdditionDto articleAdditionDto = new ArticleAdditionDto();
        articleAdditionDto.getInventory().add(articleDto);

        return articleAdditionDto;
    }

}
