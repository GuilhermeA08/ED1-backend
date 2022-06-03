package com.ed1.article.controller;

import com.ed1.article.model.Article;
import com.ed1.article.service.ArticleService;
import com.ed1.article.structures.List.DoubleLinkedList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public ResponseEntity<DoubleLinkedList<Article>> findAll() throws JsonProcessingException {
		DoubleLinkedList<Article> articleList = articleService.findAll();
		return ResponseEntity.ok().body(articleList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Article> findById(@PathVariable Long id) {
		Article articleDto = articleService.findById(id);
		return ResponseEntity.ok().body(articleDto);
	}

	@PostMapping
	public ResponseEntity<Article> save(
			@RequestParam(name = "article", required = true) String articleDTO,
			@RequestParam(name = "attachment", required = false) MultipartFile attachment) throws JsonMappingException, JsonProcessingException {
		
		Article article = objectMapper.readValue(articleDTO, Article.class);
		
		Article articleDto = articleService.save(article, attachment);
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
			@RequestParam(name = "article", required = true) String articleDTO,
			@RequestParam(name = "attachment", required = false) MultipartFile attachment) throws JsonMappingException, JsonProcessingException {
		
		Article article = objectMapper.readValue(articleDTO, Article.class);
		
		Article articleDto = articleService.update(id, article, attachment);
		return ResponseEntity.ok().body(articleDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
		articleService.deleteArticle(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}/attachment")
	public ResponseEntity<Void> deleteArticleAttachment(@PathVariable Long id) {
		articleService.deleteArticleAttachment(id);
		return ResponseEntity.noContent().build();
	}
}
