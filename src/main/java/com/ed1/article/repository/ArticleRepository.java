package com.ed1.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ed1.article.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{

}
