package com.rewards.zuulapigateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AutherizationFilter extends BasicAuthenticationFilter {
	
	Environment env;

	public AutherizationFilter(AuthenticationManager authenticationManager,Environment env) {
		super(authenticationManager);
		this.env =env;
	}
	
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
					throws IOException, ServletException {
		String authHeader = request.getHeader("Authorization");
		if(authHeader==null || !authHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
	}
	
	
	public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String authHeader = request.getHeader("Authorization");
		if(authHeader==null) {
			return null;
		}
		String token =authHeader.replace("Bearer", "");
		String userId = Jwts.parser().setSigningKey("SecretKey").parseClaimsJws(token).getBody().getSubject();
		
		if(userId==null) {
			return null;
			
		}
		return new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());
	}
	
	

}
