package com.bookstore.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**","/img/**", "/js/**","lib/**", "login/**");
	}
	
	@Bean
	public UserDetailsService customerDetailsService() { 
		return new CustomerUserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();
		authentication.setPasswordEncoder(encoder());
		authentication.setUserDetailsService(customerDetailsService());
		
		return authentication;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
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
