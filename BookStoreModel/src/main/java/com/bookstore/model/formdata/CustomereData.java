package com.bookstore.model.formdata;

import com.bookstore.model.entities.Customer;

public class CustomereData {
	
	private String email;
	
	private String password;
	
	private String fistNane;
	
	private String lastName;
	
	private String phoneNumber;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFistNane() {
		return fistNane;
	}

	public void setFistNane(String fistNane) {
		this.fistNane = fistNane;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return email + " " + password + " " + fistNane + " " + lastName + " " + phoneNumber;
	}
	
	public Customer getCustomer() {
		Customer c = new Customer();
		c.setPassword(password);
		c.setEmail(email);
		c.setFirstName(fistNane);
		c.setLastName(lastName);
		c.setPhoneNumber(phoneNumber);
		return c;
	}

}
