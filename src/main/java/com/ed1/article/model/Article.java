package com.ed1.article.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_article")
@Getter
@Setter
public class Article extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String contents;
	private File attachment;
	private Integer likes;
	private Integer dislikes;
	
	public void copy(Article dto) {
		setContents(dto.getContents());
		setAttachment(dto.getAttachment());
		setLikes(dto.getLikes());
		setDislikes(dto.getDislikes());
	}

}
