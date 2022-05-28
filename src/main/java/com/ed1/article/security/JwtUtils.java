package com.ed1.article.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

	public static String JWT_SIGNING_KEY;
	
	public static Integer JWT_EXPIRATION;
	
	@Value("${jwt.signing.key}")
    public void setSigningKey(String jwtSigningKey) {
		JWT_SIGNING_KEY = jwtSigningKey;
    }
	
	@Value("${jwt.expiration}")
    public void setExpiration(Integer jwtExpiration) {
		JWT_EXPIRATION = jwtExpiration;
    }
}
