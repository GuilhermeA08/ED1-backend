package com.ed1.article.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_article")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String contents;
	private String attachment;
	private Integer likes;
	private Integer dislikes;
	
	@JsonIdentityReference(alwaysAsId = true)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne
	private User user;
	
	public void copy(Article dto) {
		setContents(dto.getContents());
		setAttachment(dto.getAttachment());
		setLikes(dto.getLikes());
		setDislikes(dto.getDislikes());
	}

}
