package com.dm.controller.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleAdditionDto {

    @JsonProperty("inventory")
    private List<ArticleDto> inventory;

    public ArticleAdditionDto() {
        inventory = new ArrayList<>();
    }

    public List<ArticleDto> getInventory() {
        return inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleAdditionDto that = (ArticleAdditionDto) o;
        return Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory);
    }

    @Override
    public String toString() {
        return "{" +
                "inventory=" + inventory +
                '}';
    }
}
