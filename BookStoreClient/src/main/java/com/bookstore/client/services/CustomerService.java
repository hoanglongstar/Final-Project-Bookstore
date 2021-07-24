package com.bookstore.client.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.client.repository.CustomerRepository;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.enumerate.AuthProvider;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer getByEmail(String email) {
		return customerRepository.getCustomerByEmail(email);
	}
	
	public void registerNewCustomer(String email, String name, AuthProvider provider ) {
		Date createDate = new Date();
		
		Customer newCustomer = new Customer();
		
		newCustomer.setEmail(email);
		newCustomer.setFirstName(name);
		newCustomer.setEmailVerified(false);
		newCustomer.setCreateDate(createDate);
		newCustomer.setLastLogin(createDate);
		newCustomer.setAuthProvider(provider);
		newCustomer.setEnabled(true);

		customerRepository.save(newCustomer);
	}
	
	public void updateCustomer(Customer customer, String name, AuthProvider authProvider) {
		Date loginDate = new Date();
		
		customer.setFirstName(name);
		customer.setLastLogin(loginDate);
		
		customerRepository.save(customer);
	}
	
	public Customer getCustomerByPhone(String phoneNumber) {
		return customerRepository.getByPhoneNumber(phoneNumber);
	}
	
	public void saveCustomer(Customer c) {
		customerRepository.save(c);
	}
}
