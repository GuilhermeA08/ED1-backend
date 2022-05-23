package com.ed1.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ed1.article.dto.UserLoginDTO;
import com.ed1.article.exception.BadRequestException;
import com.ed1.article.model.User;
import com.ed1.article.repository.UserRepository;
import com.ed1.article.security.UserDetailsData;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = null;
		Optional<UserLoginDTO> userDTO = null;
		List<String> errors = new ArrayList<>();
		
		user = userRepository.findByEmail(email);
		
		// Verificação para ser passado para o OptionalDTO 
		if(user != null) {
			userDTO = Optional.of(new UserLoginDTO(user));
		}
		
		if(userDTO == null) {
			errors.add("Email [" + email + "] not found");
			throw new BadRequestException(errors);
		}
			
			
		return new UserDetailsData(userDTO);
	}

}
