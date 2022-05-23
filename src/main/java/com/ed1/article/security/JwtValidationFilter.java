package com.ed1.article.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ed1.article.model.Permissions;

public class JwtValidationFilter extends BasicAuthenticationFilter{
	
	public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader(HEADER_ATTRIBUTE);

        if (authorization == null || !authorization.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        
        String token = authorization.replace(TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
    	
        DecodedJWT jwtDecoded = JWT.require(Algorithm.HMAC512(JwtUtils.JWT_SIGNING_KEY)).build().verify(token);
        String subject = jwtDecoded.getSubject();
        Permissions[] permissions = jwtDecoded.getClaim("permission").asArray(Permissions.class);
        
        Collection<Permissions> authorities = new ArrayList<>();
        authorities.add(permissions[0]);

        if (subject == null) {
            return null;
        }
        
        return new UsernamePasswordAuthenticationToken(subject, null, authorities);
    }
}
