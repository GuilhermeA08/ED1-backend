package com.ed1.article.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
	public void preSave() {
		createdAt = new Date(System.currentTimeMillis());
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedAt = new Date(System.currentTimeMillis());
	}
}
