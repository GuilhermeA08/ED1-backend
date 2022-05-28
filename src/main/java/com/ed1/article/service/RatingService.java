package com.ed1.article.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ed1.article.model.Article;
import com.ed1.article.model.Rating;
import com.ed1.article.repository.ArticleRepository;
import com.ed1.article.repository.RatingRepository;
import com.ed1.article.repository.UserRepository;
import com.ed1.article.structures.List.DoubleLinkedList;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ArticleRepository articleRepository;

	@Transactional(readOnly = true)
	public DoubleLinkedList<Rating> findAll() {
		DoubleLinkedList<Rating> articleList = new DoubleLinkedList<>();
		articleList.addAll(ratingRepository.findAll());
		return articleList;
	}

	@Transactional(readOnly = true)
	public Rating findById(Long id) {
		Optional<Rating> opt = ratingRepository.findById(id);
		Rating rating = opt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return rating;
	}

	@Transactional
	public Rating save(Rating ratingDto) {
		// Verifica se o artigo existe
		Article articleEntity = articleRepository
				.findById(ratingDto.getArticle().getId())
				.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		// Verifica se já avaliou o artigo
		articleEntity.getRatings().forEach(rating -> {
			if(rating.getUser().getId() == ratingDto.getUser().getId()) {
				throw new RuntimeException("User has already rated the article");
			}
		});

		return ratingRepository.save(ratingDto);
	}
	
	@Transactional
	public Rating update(Long id, Rating ratingDto) {
		Rating ratingEntity = ratingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		ratingEntity.copy(ratingDto);
		
		// Relacionamento com usuário
		ratingEntity.setUser(
			userRepository.findById(ratingDto.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"))
		);
		
		// Relacionamento com artigo
		ratingEntity.setArticle(
			articleRepository.findById(ratingDto.getArticle().getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"))
		);
		
		return ratingRepository.save(ratingEntity);
	}

	@Transactional
	public void deleteRating(Long id) {
		Optional<Rating> ratingOpt = ratingRepository.findById(id);
		Rating ratingEntity = ratingOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		ratingRepository.delete(ratingEntity);
	}
}
