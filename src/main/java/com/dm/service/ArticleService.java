package com.dm.service;

import com.dm.WarehouseManagerException;
import com.dm.controller.article.dto.ArticleAdditionDto;
import com.dm.controller.article.dto.ArticleDto;
import com.dm.model.Article;
import com.dm.repository.ArticleRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // Creates article if name does not exist or adds stock to current inventory if article with given name is found.
    @Transactional
    public ArticleAdditionDto addInventory(ArticleAdditionDto articleAdditionDto) {
        logger.info("Request to add inventory: {}", articleAdditionDto);

        ArticleAdditionDto inventoryNewState = new ArticleAdditionDto();

        articleAdditionDto.getInventory().forEach(articleDto -> {
            if (!StringUtils.isNumeric(articleDto.getStock())) {
                throw new WarehouseManagerException("Stock value provided '" + articleDto.getStock() + "' not valid");
            }

            final Article articleDB = articleRepository.findArticleByName(articleDto.getName());
            if (articleDB != null) {
                articleDB.addStock(Long.parseLong(articleDto.getStock()));
                inventoryNewState.getInventory().add(new ArticleDto(articleRepository.save(articleDB)));
            } else {
                Article articleNew = new Article(articleDto.getName(), Long.parseLong(articleDto.getStock()));
                articleRepository.save(articleNew);
                inventoryNewState.getInventory().add(new ArticleDto(articleNew));
            }
        });

        logger.info("New state of inventory for relevant articles: {}", inventoryNewState);
        return inventoryNewState;
    }
}
