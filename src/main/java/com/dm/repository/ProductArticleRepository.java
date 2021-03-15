package com.dm.repository;

import com.dm.model.ProductArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductArticleRepository extends JpaRepository<ProductArticle, Long> {

}
