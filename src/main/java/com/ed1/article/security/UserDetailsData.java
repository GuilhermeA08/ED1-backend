package com.ed1.article.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ed1.article.dto.UserLoginDTO;
import com.ed1.article.model.Permissions;


public class UserDetailsData implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private Optional<UserLoginDTO> user;
	
	public UserDetailsData(Optional<UserLoginDTO> user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<Permissions> authorities = new ArrayList<>();
		authorities.add(user.get().getPermission());
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.orElse(new UserLoginDTO()).getPassword();
	}

	@Override
	public String getUsername() {
		return user.orElse(new UserLoginDTO()).getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UserLoginDTO getUser() {
		return user.get();
	}
	
}
