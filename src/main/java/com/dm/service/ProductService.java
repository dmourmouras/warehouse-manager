package com.dm.service;

import com.dm.WarehouseManagerException;
import com.dm.controller.product.dto.*;
import com.dm.model.Article;
import com.dm.model.Product;
import com.dm.model.ProductArticle;
import com.dm.repository.ArticleRepository;
import com.dm.repository.ProductArticleRepository;
import com.dm.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private ProductArticleRepository productArticleRepository;
    private ArticleRepository articleRepository;

    public ProductService(ProductRepository productRepository, ProductArticleRepository productArticleRepository,
                          ArticleRepository articleRepository) {
        this.productRepository = productRepository;
        this.productArticleRepository = productArticleRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductAdditionDto createProducts(ProductAdditionDto productAdditionDto) {
        logger.info("Request to create new products: {}", productAdditionDto);

        ProductAdditionDto productsNewState = new ProductAdditionDto();

        productAdditionDto.getProducts().forEach(productDto -> {
            if (productRepository.existsByName(productDto.getName())) {
                throw new WarehouseManagerException("Product '" + productDto.getName() + "' already exists");
            }
            final Product productNew = new Product(productDto.getName(), new BigDecimal(productDto.getPrice()));
            final Product productSaved = productRepository.save(productNew);
            final ProductDto productSavedDto = new ProductDto(productSaved);
            productsNewState.getProducts().add(productSavedDto);

            productDto.getArticles().forEach(articleDto -> {
                final Optional<Article> articleDBOptional = articleRepository.findById(Long.parseLong(articleDto.getId()));
                if (articleDBOptional.isEmpty()) {
                    throw new WarehouseManagerException("Article with id: " + articleDto.getId() + " does not exist. " +
                            "All the necessary articles should exist before creating the product");
                }
                validateNumeric(articleDto.getAmount(), "amount_of");
                ProductArticle productArticleNew = new ProductArticle(productSaved, articleDBOptional.get(),
                        Integer.parseInt(articleDto.getAmount()));
                productArticleRepository.save(productArticleNew);
                productSavedDto.getArticles().add(new ArticleDto(articleDBOptional.get().getId().toString(),
                        articleDto.getAmount()));
            });
        });

        logger.info("New products created: {}", productsNewState);
        return productsNewState;
    }

    // 'Sells' a product in the quantity specified. In practical terms, removes the relevant articles from the warehouse
    public void sellProduct(Integer productId, Integer quantity) {
        logger.info("Request to sell product: {}, quantity: {}", productId, quantity);

        if (quantity < 1) {
            throw new WarehouseManagerException("Quantity should be >= 1");
        }

        final Product productDB = productRepository.findById(productId)
                .orElseThrow(() -> new WarehouseManagerException("Product with id: " + productId + " does not exist"));

        productDB.getProductArticles().forEach(productArticle -> {
            final Article article = productArticle.getArticle();
            if (article.getStock() < productArticle.getArticleAmountNeeded() * quantity) {
                throw new WarehouseManagerException("Insufficient stock for article with id: " + article.getId());
            }
            article.removeStock(productArticle.getArticleAmountNeeded().longValue() * quantity);
        });

        productRepository.save(productDB);
        logger.info("Successfully sold product: {}, quantity: {}", productId, quantity);
    }

    // Returns the maximum quantity for a product based on the available articles in the warehouse
    public AvailableQuantityDto getProductAvailability(Integer productId) {
        Long availableQuantityProduct = Long.MAX_VALUE;

        final Product productDB = productRepository.findById(productId)
                .orElseThrow(() -> new WarehouseManagerException("Product with id: " + productId + " does not exist"));

        for (ProductArticle productArticle : productDB.getProductArticles()) {
            final Article article = productArticle.getArticle();
            final Long availableQuantityArticle = article.getStock() / productArticle.getArticleAmountNeeded();
            if (availableQuantityArticle < availableQuantityProduct) {
                availableQuantityProduct = availableQuantityArticle;
            }
        }

        return new AvailableQuantityDto(productId.toString(), availableQuantityProduct.toString());
    }

    // Checks if a whole order(a collection of products in the quantities wanted) is possible based on the available stock
    public Boolean checkIfOrderIsPossible(ProductAvailabilityRequestDto productAvailabilityRequestDto) {
        final List<Article> allArticles = articleRepository.findAll();
        Map<Long, Long> articleStock = allArticles.stream().collect(Collectors.toMap(Article::getId, Article::getStock));

        for (ProductAvailabilityDto product : productAvailabilityRequestDto.getProducts()) {
            validateNumeric(product.getId(), "id");
            final Product productDB = productRepository.findById(Integer.parseInt(product.getId()))
                    .orElseThrow(() -> new WarehouseManagerException("Product with id: " +
                            Integer.parseInt(product.getId()) + " does not exist"));

            for (ProductArticle productArticle : productDB.getProductArticles()) {
                validateNumeric(product.getQuantity(), "quantity");
                if (articleStock.get(productArticle.getArticle().getId()) -
                        productArticle.getArticleAmountNeeded() * Integer.parseInt(product.getQuantity()) < 0) {
                    return false;
                } else {
                    articleStock.put(productArticle.getArticle().getId(), articleStock.get(productArticle.getArticle().getId()) -
                            productArticle.getArticleAmountNeeded() * Integer.parseInt(product.getQuantity()));
                }
            }
        }

        return true;
    }

    // Validates that provided string is parsable and not negative
    private void validateNumeric(String strValue, String strName) {
        if (!StringUtils.isNumeric(strValue)) {
            throw new WarehouseManagerException(strName + " field provided '" + strValue + "' not valid");
        }
    }
}
