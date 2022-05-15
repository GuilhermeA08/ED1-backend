package com.ed1.article.controller;

import com.ed1.article.model.Article;
import com.ed1.article.service.ArticleService;
import com.ed1.article.structures.List.DoubleLinkedList;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/articles")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping
  public ResponseEntity<DoubleLinkedList<Article>> findAll() {
    DoubleLinkedList<Article> articleList = articleService.findAll();
    return ResponseEntity.ok().body(articleList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Article> findById(@PathVariable Long id) {
    Article articleDto = articleService.findById(id);
    return ResponseEntity.ok().body(articleDto);
  }

  @PostMapping
  public ResponseEntity<Article> save(@RequestBody Article article) {
    Article articleDto = articleService.save(article);
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(articleDto.getId())
      .toUri();
    return ResponseEntity.created(uri).body(articleDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Article> update(
    @PathVariable Long id,
    @RequestBody Article article
  ) {
    Article articleDto = articleService.update(id, article);
    return ResponseEntity.ok().body(articleDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    articleService.deleteArticle(id);
    return ResponseEntity.noContent().build();
  }
}
