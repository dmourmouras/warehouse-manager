package com.dm.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AvailableQuantityDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("available_quantity")
    private String availableQuantity;

    public AvailableQuantityDto(String id, String availableQuantity) {
        this.id = id;
        this.availableQuantity = availableQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableQuantityDto that = (AvailableQuantityDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(availableQuantity, that.availableQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, availableQuantity);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", availableQuantity='" + availableQuantity + '\'' +
                '}';
    }
}
