package com.dm.repository;

import com.dm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByName(String name);

    boolean existsByName(String name);
}
