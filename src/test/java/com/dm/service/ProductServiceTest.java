package com.dm.service;

import com.dm.WarehouseManagerException;
import com.dm.controller.article.dto.ArticleAdditionDto;
import com.dm.controller.product.dto.ProductAvailabilityDto;
import com.dm.controller.product.dto.ProductAvailabilityRequestDto;
import com.dm.model.Article;
import com.dm.model.Product;
import com.dm.model.ProductArticle;
import com.dm.repository.ArticleRepository;
import com.dm.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("checkIfOrderIsPossible() should throw exception when a product id provided is not a positive number")
    void checkIfOrderIsPossibleTest1() {
        ProductAvailabilityRequestDto productAvailabilityRequestDto =
                getProductAvailabilityRequestDtoStub(getProductAvailabilityDtoStub("id", "10"));

        Assertions.assertThrows(WarehouseManagerException.class, () -> {
            productService.checkIfOrderIsPossible(productAvailabilityRequestDto);
        });
    }

    @Test
    @DisplayName("checkIfOrderIsPossible() should return true when the order of products is possible")
    void checkIfOrderIsPossibleTest2() {
        ProductAvailabilityRequestDto productAvailabilityRequestDto =
                getProductAvailabilityRequestDtoStub(getProductAvailabilityDtoStub("1", "10"));
        Article articleStub = getArticleStub(7L, "article1", 60L);
        ProductArticle productArticleStub = getProductArticleStub(articleStub, null, 5);
        Product productStub = getProductStub(1, "product1", BigDecimal.valueOf(9.99), List.of(productArticleStub));

        when(articleRepository.findAll()).thenReturn(List.of(articleStub));
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(productStub));

        // Quantity of product1 wanted: 10. Stock of article1 needed per product1: 5.
        // Thus stock should be >=50 for the order to be possible. Stock in the stub: 60
        final Boolean isOrderPossible = productService.checkIfOrderIsPossible(productAvailabilityRequestDto);

        assertTrue(isOrderPossible);
    }

    @Test
    @DisplayName("checkIfOrderIsPossible() should return false when the order of products is not possible")
    void checkIfOrderIsPossibleTest3() {
        ProductAvailabilityRequestDto productAvailabilityRequestDto =
                getProductAvailabilityRequestDtoStub(getProductAvailabilityDtoStub("1", "10"));
        Article articleStub = getArticleStub(7L, "article1", 40L);
        ProductArticle productArticleStub = getProductArticleStub(articleStub, null, 5);
        Product productStub = getProductStub(1, "product1", BigDecimal.valueOf(9.99), List.of(productArticleStub));

        when(articleRepository.findAll()).thenReturn(List.of(articleStub));
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(productStub));

        // Quantity of product1 wanted: 10. Stock of article1 needed per product1: 5.
        // Thus stock should be >=50 for the order to be possible. Stock in the stub: 40
        final Boolean isOrderPossible = productService.checkIfOrderIsPossible(productAvailabilityRequestDto);

        assertFalse(isOrderPossible);
    }

    @Test
    @DisplayName("checkIfOrderIsPossible() should throw exception when a product id provided does not exist in DB")
    void checkIfOrderIsPossibleTest4() {
        ProductAvailabilityRequestDto productAvailabilityRequestDto =
                getProductAvailabilityRequestDtoStub(getProductAvailabilityDtoStub("1", "10"));
        Article articleStub = getArticleStub(7L, "article1", 40L);

        when(articleRepository.findAll()).thenReturn(List.of(articleStub));
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(WarehouseManagerException.class, () -> {
            productService.checkIfOrderIsPossible(productAvailabilityRequestDto);
        });

        verify(productRepository, times(1)).findById(any());
    }

    // ToDo Write unit tests for the other methods of ProductService

    private ProductAvailabilityRequestDto getProductAvailabilityRequestDtoStub(ProductAvailabilityDto productAvailabilityDto) {
        final ProductAvailabilityRequestDto productAvailabilityRequestDtoStub = new ProductAvailabilityRequestDto();
        productAvailabilityRequestDtoStub.getProducts().add(productAvailabilityDto);

        return productAvailabilityRequestDtoStub;
    }

    private ProductAvailabilityDto getProductAvailabilityDtoStub(String id, String quantity) {
        return new ProductAvailabilityDto(id, quantity);
    }

    private Product getProductStub(Integer id, String name, BigDecimal price, List<ProductArticle> articles) {
        Product productStub = new Product(name, price);
        productStub.setId(id);
        productStub.setProductArticles(articles);

        return productStub;
    }

    private Article getArticleStub(Long id, String name, Long stock) {
        return new Article(id, name, stock);
    }

    private ProductArticle getProductArticleStub(Article article, Product product, Integer articleAmountNeeded) {
        return new ProductArticle(product, article, articleAmountNeeded);
    }

}
