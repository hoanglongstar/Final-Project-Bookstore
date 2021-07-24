package com.bookstore.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.bookstore.client.handler.OnAuthenticationFailureHandler;
import com.bookstore.client.handler.OnAuthenticationSuccessHandler;
import com.bookstore.client.services.CustomerOAuth2Service;

@EnableWebSecurity
@Configuration
public class WebSerurityConfigure extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomerOAuth2Service customerOAuth2Service;
	
	@Autowired
	private OnAuthenticationSuccessHandler successHandler;
	
	//@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/home").permitAll()
		.anyRequest().permitAll()
		.and().formLogin().loginPage("/login").permitAll()
		.usernameParameter("email")
		.passwordParameter("password")
		.loginProcessingUrl("/dologin")
		.defaultSuccessUrl("/").permitAll()
		.and().oauth2Login().loginPage("/login").permitAll()
		.userInfoEndpoint().userService(customerOAuth2Service)
		.and().successHandler(successHandler)
		.failureHandler(failureHandler)
		.and().logout().permitAll();
	}
}
