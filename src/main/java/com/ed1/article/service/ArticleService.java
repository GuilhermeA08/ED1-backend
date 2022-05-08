package com.ed1.article.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ed1.article.model.Article;
import com.ed1.article.repository.ArticleRepository;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Transactional(readOnly = true)
	public List<Article> findAll() {
		List<Article> articleList = articleRepository.findAll();
		return articleList;
	}
	
	@Transactional(readOnly = true)
	public Article findById(Long id) {
		Optional<Article> opt = articleRepository.findById(id);
		Article article = opt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return article;
	}
	
	@Transactional
	public Article save(Article articleDto) {
		Article articleDb = articleRepository.save(articleDto);
		return articleDb;
	}
	
	@Transactional
	public Article update(Long id, Article articleDto) {
		Optional<Article> articleOpt = articleRepository.findById(id);
		Article articleEntity = articleOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		articleEntity.copy(articleDto);
		
		return articleRepository.save(articleEntity);
	}
	
	@Transactional
	public void deleteArticle(Long id) {
		Optional<Article> articleOpt = articleRepository.findById(id);
		Article articleEntity = articleOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		articleRepository.delete(articleEntity);
	}
	
}
