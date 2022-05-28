package com.ed1.article.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ed1.article.dto.UserLoginDTO;
import com.ed1.article.exception.BadRequestException;
import com.ed1.article.exception.HttpErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@PropertySource(value = {"classpath:application.properties"})
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			UserLoginDTO user = new ObjectMapper().readValue(request.getInputStream(), UserLoginDTO.class);
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new ArrayList<>()));
			return auth;
		} catch (IOException e) {
			List<String> error = new ArrayList<>();
			error.add(e.getMessage());
			throw new BadRequestException(error);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		UserDetailsData userData = (UserDetailsData) authResult.getPrincipal();
		
		List<String> permission = userData.getAuthorities().stream().map(item -> {
			return item.getAuthority();
		}).collect(Collectors.toList());
		
		String token = JWT.create()
				.withSubject(userData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtUtils.JWT_EXPIRATION))
				.withClaim("permission", permission)
				.sign(Algorithm.HMAC512(JwtUtils.JWT_SIGNING_KEY));
		
		response.setContentType("application/json");
		response.getWriter().write(
				"{\"token\":\"" + token + "\"," + 
				"\"permission\":\""+ permission.get(0) + "\"," + 
				"\"id\":" + userData.getUser().getId() + "}");
		response.getWriter().flush();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
			AuthenticationException ex) throws IOException, ServletException {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		
		HttpErrorInfo response = HttpErrorInfo.builder().message(errors).build();
		
		res.setContentType("application/json;charset=UTF-8");
	    res.setStatus(403);
	    res.getWriter().write(response.toString());
	}
}
