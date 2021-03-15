package com.dm.controller.product;

import com.dm.WarehouseManagerException;
import com.dm.controller.product.dto.ProductAdditionDto;
import com.dm.controller.product.dto.AvailableQuantityDto;
import com.dm.controller.product.dto.ProductAvailabilityRequestDto;
import com.dm.controller.product.dto.ProductAvailabilityResponseDto;
import com.dm.model.Product;
import com.dm.repository.ProductRepository;
import com.dm.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;
    private ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new WarehouseManagerException("Product with id: " + id + " not found"));
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public ResponseEntity<ProductAdditionDto> createProducts(@RequestBody ProductAdditionDto productAdditionDto) {

        final ProductAdditionDto productsNewState = productService.createProducts(productAdditionDto);

        return new ResponseEntity<>(productsNewState, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        logger.info("Request to delete product if exists: {}", id);

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/products/{id}/sale")
    public ResponseEntity<Void> sellProduct(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer quantity) {

        productService.sellProduct(id, quantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/products/{id}/available_quantity")
    public ResponseEntity<AvailableQuantityDto> getProductAvailability(@PathVariable Integer id) {

        final AvailableQuantityDto availableQuantityDto = productService.getProductAvailability(id);

        return new ResponseEntity<>(availableQuantityDto, HttpStatus.OK);
    }

    @PostMapping("/products/availability")
    public ResponseEntity<ProductAvailabilityResponseDto> checkOrder(@RequestBody ProductAvailabilityRequestDto productAvailabilityRequestDto) {

        final Boolean isOrderPossible = productService.checkIfOrderIsPossible(productAvailabilityRequestDto);

        return new ResponseEntity<>(new ProductAvailabilityResponseDto(isOrderPossible.toString()), HttpStatus.OK);
    }
}

