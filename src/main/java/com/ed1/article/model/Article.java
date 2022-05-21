package com.ed1.article.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	private String title;
	
	@Column(length = 5000)
	private String contents;
	private String attachment;
	
	@JsonIdentityReference(alwaysAsId = true)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<>();
	
	public void copy(Article dto) {
		setTitle(dto.getTitle());
		setContents(dto.getContents());
		setAttachment(dto.getAttachment());
	}

}
