package com.dm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "ProductArticle")
@Table(name = "product_article")
public class ProductArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;


    @Column(name = "article_amount_needed")
    @JsonProperty("amount_needed")
    private Integer articleAmountNeeded;

    public ProductArticle() {
    }

    public ProductArticle(Product product, Article article, Integer articleAmountNeeded) {
        this.product = product;
        this.article = article;
        this.articleAmountNeeded = articleAmountNeeded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getArticleAmountNeeded() {
        return articleAmountNeeded;
    }

    public void setArticleAmountNeeded(Integer articleAmountNeeded) {
        this.articleAmountNeeded = articleAmountNeeded;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductArticle that = (ProductArticle) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(articleAmountNeeded, that.articleAmountNeeded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleAmountNeeded);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", articleAmountNeeded=" + articleAmountNeeded +
                ", product=" + product +
                ", article=" + article +
                '}';
    }
}
