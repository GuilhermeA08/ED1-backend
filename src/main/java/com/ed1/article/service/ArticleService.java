package com.ed1.article.service;

import com.ed1.article.model.Article;
import com.ed1.article.repository.ArticleRepository;
import com.ed1.article.repository.RatingRepository;
import com.ed1.article.repository.UserRepository;
import com.ed1.article.structures.List.DoubleLinkedList;
import com.ed1.article.util.FileUtil;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private FileUtil fileUtil;

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
	public Article save(Article articleDto, MultipartFile attachment) {
		
		Article articleEntity = articleRepository.save(articleDto);

		if(attachment != null) {
			saveArticleAttachment(articleEntity, attachment);
			articleEntity = articleRepository.save(articleDto);
		}

		return articleEntity;
	}

	@Transactional
	public Article update(Long id, Article articleDto, MultipartFile attachment) {
		Optional<Article> articleOpt = articleRepository.findById(id);
		Article articleEntity = articleOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

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
		
		if(attachment != null) {
			saveArticleAttachment(articleEntity, attachment);
		}

		return articleRepository.save(articleEntity);
	}

	@Transactional
	public void deleteArticle(Long id) {
		Optional<Article> articleOpt = articleRepository.findById(id);
		Article articleEntity = articleOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

		deleteAttachment(articleEntity);
		articleRepository.delete(articleEntity);
	}
	
	public void deleteArticleAttachment(Long id) {
		Optional<Article> articleOpt = articleRepository.findById(id);
		Article articleEntity = articleOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		deleteAttachment(articleEntity);
	}
	
	private void saveArticleAttachment(Article entity, MultipartFile attachment) {
		String path = "article/" +  entity.getId() + "/";
		String fileName = "attachment." + FileUtil.getFileExtension(attachment.getOriginalFilename());

		// Remove o arquivo que já está salvo caso ele exista
		if(entity.getAttachment() != null) {
			FileUtil.removeFile(entity.getAttachment());
		}

		fileUtil.saveFile(path, fileName, attachment);
		entity.setAttachment(path + fileName);
	}

	private void deleteAttachment(Article entity) {
		if(FileUtil.removeFile(entity.getAttachment())) {			
			entity.setAttachment(null);
		}
	}
}
