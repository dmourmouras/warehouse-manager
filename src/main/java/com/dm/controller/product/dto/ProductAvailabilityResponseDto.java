package com.dm.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProductAvailabilityResponseDto {

    @JsonProperty("is_order_possible")
    private String isOrderPossible;


    public ProductAvailabilityResponseDto() {
    }

    public ProductAvailabilityResponseDto(String isOrderPossible) {
        this.isOrderPossible = isOrderPossible;
    }

    public String getIsOrderPossible() {
        return isOrderPossible;
    }

    public void setIsOrderPossible(String isOrderPossible) {
        this.isOrderPossible = isOrderPossible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAvailabilityResponseDto that = (ProductAvailabilityResponseDto) o;
        return Objects.equals(isOrderPossible, that.isOrderPossible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOrderPossible);
    }

    @Override
    public String toString() {
        return "{" +
                "isOrderPossible='" + isOrderPossible + '\'' +
                '}';
    }
}
