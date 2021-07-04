package com.bookstore.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.bookstore.admin.handler.OnAuthenticationFailureHandler;
import com.bookstore.admin.handler.OnAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	
	@Autowired
	private OnAuthenticationSuccessHandler successHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/signup", "/assets/**", "/css/**", "/fonts/**", "/images/**", "/js/**", "/scss/**", "/vendor/**").permitAll()
		.antMatchers("/dashboard", "/users", "/create_new_user").hasAnyAuthority("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll()
		.usernameParameter("username")
		.passwordParameter("password")
		.loginProcessingUrl("/dologin")
		.failureHandler(failureHandler)
		.successHandler(successHandler)
		.and().logout().permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}
}
