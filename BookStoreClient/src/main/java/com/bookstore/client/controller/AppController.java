package com.bookstore.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

	@RequestMapping("/")
	public String showHomeView() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String showLoginView() {
		return "login";
	}
}
