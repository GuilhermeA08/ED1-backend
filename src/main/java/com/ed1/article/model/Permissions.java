package com.ed1.article.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {
	ADMIN("ADMIN"),
	USER("USER");
	
	private String permission;

	Permissions(String permission) {
		this.permission = permission;
	}

	@Override
	public String getAuthority() {
		return permission;
	}
	
}
