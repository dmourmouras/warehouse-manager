package com.dm.repository;

import com.dm.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findArticleByName(String name);

}
