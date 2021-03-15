package com.dm.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductAvailabilityRequestDto {

    @JsonProperty("products")
    List<ProductAvailabilityDto> products;

    public ProductAvailabilityRequestDto() {
        products = new ArrayList<>();
    }

    public List<ProductAvailabilityDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductAvailabilityDto> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAvailabilityRequestDto that = (ProductAvailabilityRequestDto) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }

    @Override
    public String toString() {
        return "{" +
                "products=" + products +
                '}';
    }
}
