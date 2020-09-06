package com.rewards.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.users.request.LoginRequest;
import com.rewards.users.service.UserService;
import com.rewards.users.serviceimpl.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*UsernamePasswordAuthenticationFilter filter is only user for url /login*/
public class AuthenticateFilter extends UsernamePasswordAuthenticationFilter {
	
	private Environment  env ;
	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	
	UserService userDetailsService;
	
	public AuthenticateFilter(UserService userDetailsService,Environment  env ,AuthenticationManager authManger) {
		this.env = env;
		this.userDetailsService =userDetailsService;
		super.setAuthenticationManager(authManger);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
		
		try {
			LoginRequest cred = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(cred.getUserName(), cred.getPassword() ,new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();		
		userDetails.getUsername();
		userDetailsService.loadUserByUsername(userDetails.getUsername());	
		String jwtToken =Jwts.builder()
		.setSubject((userDetails.getUsername()))
		.setIssuedAt(new Date())
		.setExpiration(new Date((new Date()).getTime() + 86400000))
		.signWith(SignatureAlgorithm.HS512, "SecretKey")
		.compact();
		response.addHeader("token", jwtToken);
		response.addHeader("userId", userDetails.getUsername());
		
	}
}
