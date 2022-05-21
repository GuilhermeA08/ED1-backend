package com.ed1.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ed1.article.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

}
