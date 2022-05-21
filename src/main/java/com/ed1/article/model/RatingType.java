package com.ed1.article.model;

import lombok.Getter;

@Getter
public enum RatingType {
	LIKE,
	DISLIKE;
	
	private String status;
}
