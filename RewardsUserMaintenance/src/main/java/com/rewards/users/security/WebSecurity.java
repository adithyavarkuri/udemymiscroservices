package com.rewards.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rewards.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment  env ;
	
	@Autowired
	UserService userDetailsService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	public WebSecurity(Environment env) {
		this.env= env;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
			.and()
			.addFilterBefore(getAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
				
	}
	
	public AuthenticateFilter getAuthenticationFilter() throws Exception {
		AuthenticateFilter authenticateFilter = new AuthenticateFilter(userDetailsService,env,authenticationManager());
		//authenticateFilter.setFilterProcessesUrl("api/auth/login");
		/*
		 * as in AuthenticateFilter we are using getauthenticationfilter so we are
		 * setting this values here other wise it will return null
		 */
		return authenticateFilter;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	
}
