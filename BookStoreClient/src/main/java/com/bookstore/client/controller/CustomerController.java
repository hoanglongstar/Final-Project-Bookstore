package com.bookstore.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.client.services.CustomerService;
import com.bookstore.model.entities.Customer;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(value = ("/my_account"))
	public String showViewMyAccount(Authentication authentication, Model model ) {
		Customer customer = customerService.getCustomerByEmail(authentication.getName());
		
		model.addAttribute("customer", customer);
		return "my-account";
	}
	
	@PostMapping(value = ("/my_account"))
	public String saveCustomerProfile(@ModelAttribute("customer") Customer customerForm) {
		
		customerService.saveCustomer(customerForm);
		return "my-account";
	}
}
