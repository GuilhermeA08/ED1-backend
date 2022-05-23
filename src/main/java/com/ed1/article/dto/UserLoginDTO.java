package com.ed1.article.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.ed1.article.model.Permissions;
import com.ed1.article.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private Long id;
	
	@NotNull(message = "Informe o email")
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotNull(message = "Informe a senha")
	private String password;
	
	@JsonIgnore
	private String name;
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private Permissions permission;

	public UserLoginDTO(User user) {
		id = user.getId();
		email = user.getEmail();
		password = user.getPassword();
		name = user.getName();
		permission = user.getPermission();
	}
}
