package com.dm.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProductAvailabilityDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("quantity")
    private String quantity;

    public ProductAvailabilityDto(String id, String quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAvailabilityDto that = (ProductAvailabilityDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}

