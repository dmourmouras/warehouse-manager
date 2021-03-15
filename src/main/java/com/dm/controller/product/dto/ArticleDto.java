package com.dm.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ArticleDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("amount_of")
    private String amount;

    public ArticleDto() {
    }

    public ArticleDto(String id, String amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDto that = (ArticleDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
