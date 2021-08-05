package com.bookstore.admin.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.CustomerRepository;
import com.bookstore.model.entities.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();
	}
	
	public void save(Customer customer) {
		Pattern bcryptPasswordPattern = Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");  
		
		if(!bcryptPasswordPattern.matcher(customer.getPassword()).matches()) {
			String encodePassword = bCryptPasswordEncoder.encode(customer.getPassword());
			
			customer.setPassword(encodePassword);
		}
		customerRepository.save(customer);
	}
	
	public void deleteCustomerById(Integer id) {
		customerRepository.deleteById(id);
	}
	
	public Customer getCustomerById(Integer id) {
		return customerRepository.getById(id);
	}
}
