package com.ed1.article.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	@NotNull(message = "Inform the email")
	@Column(unique = true)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	public void copy(User dto) {
		setName(dto.getName());
		setEmail(dto.getEmail());
		setPassword(dto.getPassword());
	}
}
