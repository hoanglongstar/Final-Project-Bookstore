package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.InvoiceRepository;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.entities.Invoice;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public List<Invoice> getAllInvoice(){
		return invoiceRepository.findAll();
	}
	
	public Invoice getInvoiceById(Integer id) {
		return invoiceRepository.getById(id);
	}
	
	public Invoice getInvoiceByCode(String code) {
		return invoiceRepository.getInvoiceByCode(code);
	}
	
	public List<Invoice> getInvoiceByCustomer(Customer customer){
		return invoiceRepository.getInvoiceByCustomerInfo(customer);
	}
}
