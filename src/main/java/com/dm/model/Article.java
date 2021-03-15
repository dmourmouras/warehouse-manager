package com.dm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Article")
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 400)
    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private Long stock;

    @JsonManagedReference
    @JsonProperty("depended_products")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductArticle> dependedProducts = new ArrayList<>();

    public Article() {
    }

    public Article(String name, Long stock) {
        this.name = name;
        this.stock = stock;
    }

    public Article(Long id, String name, Long stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public void addStock(Long stockToBeAdded) {
        stock += stockToBeAdded;
    }

    public void removeStock(Long stockToBeRemoved) {
        stock -= stockToBeRemoved;
    }

    public List<ProductArticle> getDependedProducts() {
        return dependedProducts;
    }

    public void setDependedProducts(List<ProductArticle> dependedProducts) {
        this.dependedProducts = dependedProducts;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                '}';
    }
}

