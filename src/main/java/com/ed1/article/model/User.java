package com.ed1.article.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	@NotNull(message = "Inform the email")
	@Column(unique = true)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Permissions permission;
	
	@JsonIdentityReference(alwaysAsId = true)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Article> articles = new HashSet<>();
	
	@JsonIdentityReference(alwaysAsId = true)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<>();
	
	@PrePersist
	public void preSave() {
		setCreatedAt(new Date(System.currentTimeMillis()));
		setPermission(Permissions.USER);
	}
	
	public void copy(User dto) {
		setName(dto.getName());
		setEmail(dto.getEmail());
		setPassword(dto.getPassword());
		setPermission(dto.getPermission());
	}
}
