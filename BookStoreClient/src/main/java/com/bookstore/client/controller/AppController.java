package com.bookstore.client.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;

import com.bookstore.client.helper.EmailServiceImpl;
import com.bookstore.client.services.CustomerService;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.formdata.CustomerForm;

@Controller
public class AppController {
	
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping(value = {"/", "/home"})
	public String showHomeView(Model model) {
		
		return "index";
	}
	
	@RequestMapping(value = {"/cart"})
	public String showCartView(Model model){
		return "cart";
	}
	
	@RequestMapping(value = {"/checkout"})
	public String checkoutView(Model model) {
		/*
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * 
		 * if (authentication instanceof AnonymousAuthenticationToken) { return
		 * "redirect:/login"; }
		 */
		
		return "checkout";
	}
	
	@RequestMapping(value = {"/product-list"})
	public String showProductList(Model model) {
		return "product-list";
	}
	
	@RequestMapping(value = {"/login"} , method = RequestMethod.GET)
	public String showLoginView(Model model) {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegisterView(Model model) {
		
		CustomerForm customerForm = new CustomerForm();
		System.out.println("check: " + customerForm.toString());
		model.addAttribute("customerForm", customerForm);
		
		return "register";
	}
//	
//	@PostMapping("/register")
//	public String doRegister(@ModelAttribute("customer") CustomerData customerData) {
//		System.out.println("AppController::doRegister -> " + customerData.toString());
//		customerService.registerNewCustomer(customerData);
//		return "redirect:/login";
//	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String checkCustomerInfo(@Valid CustomerForm customerForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)   {
	
		boolean error = false;
		
		Customer customer = customerService.getCustomerByPhone(customerForm.getPhoneNumber());
		Customer customer2 = customerService.getCustomerByEmail(customerForm.getEmail());
		
		if(customer2 != null) {
			redirectAttributes.addFlashAttribute("error_duplicate_email", "the email used by another one");
			error=true;
		}
		
		if (customer != null) {
			redirectAttributes.addFlashAttribute("error_duplicate_phone", "the phone number used bt another one");
			error=true;
		}
		
		if(error) {
			return "redirect:/register";
		}
		
		System.out.println("checkCustomerInfo: " + customerForm.toString());
		
		if (bindingResult.hasErrors()) {
			return "register";
		}
		//register new account;
		customerService.saveCustomer(customerForm.getCustomer());
		//generate verification code: qwerty
		//send <a>http:localhost:8080/email_veryfication/qwerty</a> hoac <a>http:localhost:8080/email_veryfication?code=qwerty</a>
		
		//EmailServiceImpl.sendSimpleMessage(javaMailSender, "", "Registration success", "Registration success");
		
		try {
			EmailServiceImpl.sendSimpleMail(javaMailSender, htmlTemplateEngine , "huy", "nguyenvothanhhuy2002@gmail.com" , Locale.getDefault());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
