package com.ed1.article.exception;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	 
	@Override
	 public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
	     ZonedDateTime timestamp = ZonedDateTime.now();
	     
	     res.setContentType("application/json;charset=UTF-8");
	     res.setStatus(403);
	     res.getWriter().write("{" + 
	    		 "\"timestamp\":" + timestamp.toInstant().toEpochMilli() + "," +
	    		 "\"message\":[\"" + authException.getLocalizedMessage() + "\"]}");
	 }
}
