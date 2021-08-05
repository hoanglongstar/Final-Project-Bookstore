package com.bookstore.admin.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.handler.AppConstant;
import com.bookstore.admin.helper.FileUploadHelper;
import com.bookstore.admin.services.CustomerService;
import com.bookstore.model.entities.Customer;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customer")
	public String showCustomerListView(Model model) {
		List<Customer> listCustomer = customerService.getAllCustomer();
		model.addAttribute("listCustomer", listCustomer);
		
		return "customers";
	}
	
	@RequestMapping("/delete_customer/{id}")
	public String deleteCustomerById(@PathVariable(name = "id") Integer id) {
		
		customerService.deleteCustomerById(id);
		
		return "redirect:/customer";
	}
	
	@GetMapping("create_customer")
	public String showCreateCustomerView(Model model) {
		Customer customer = new Customer();
//		Address address = new Address();
		
		model.addAttribute("customer", customer);
//		model.addAttribute("address", address);		
		
		return "create_customer";
	}
	
	@RequestMapping(value = "/create_customer", method = RequestMethod.POST)
	public String checkNewCustomer(@Valid Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Model model) {
		if(bindingResult.hasErrors()) {
			return "create_customer";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		if(!fileName.equals("")) {
			customer.setPhotoUrl(fileName);
			String uploadDir = AppConstant.CUSTOMER_PHOTO_DIR + "/" + customer.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		customer.setEnabled(true);
		customerService.save(customer);
		
		return "redirect:/customer";
	}
}
