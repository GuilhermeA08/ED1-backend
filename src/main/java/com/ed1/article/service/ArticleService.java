package com.ed1.article.service;

import com.ed1.article.model.Article;
import com.ed1.article.repository.ArticleRepository;
import com.ed1.article.repository.RatingRepository;
import com.ed1.article.repository.UserRepository;
import com.ed1.article.structures.List.DoubleLinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

  @Autowired
  private ArticleRepository articleRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private RatingRepository ratingRepository;

  @Transactional(readOnly = true)
  public DoubleLinkedList<Article> findAll() {
    DoubleLinkedList<Article> articleList = new DoubleLinkedList<Article>();
    articleList.addAll(articleRepository.findAll());
    return articleList;
  }

  @Transactional(readOnly = true)
  public Article findById(Long id) {
    Optional<Article> opt = articleRepository.findById(id);
    Article article = opt.orElseThrow(
      () -> new EntityNotFoundException("Entity not found")
    );
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
    Article articleEntity = articleOpt.orElseThrow(
      () -> new EntityNotFoundException("Entity not found")
    );

    articleEntity.copy(articleDto);
    
    // Relacionamento com usuário
    articleEntity.setUser(
    	userRepository.findById(articleDto.getUser().getId())
    		.orElseThrow(() -> new EntityNotFoundException("Entity not found"))
    );
    
    // Relacionamento com avaliação
    articleEntity.setRatings(
    	articleDto.getRatings().stream().map(rating -> {
    		return ratingRepository.findById(rating.getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    	}).collect(Collectors.toSet())
    );

    return articleRepository.save(articleEntity);
  }

  @Transactional
  public void deleteArticle(Long id) {
    Optional<Article> articleOpt = articleRepository.findById(id);
    Article articleEntity = articleOpt.orElseThrow(
      () -> new EntityNotFoundException("Entity not found")
    );

    articleRepository.delete(articleEntity);
  }
}
