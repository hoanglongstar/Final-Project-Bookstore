package com.bookstore.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.admin.services.CustomerService;
import com.bookstore.admin.services.InvoiceDetailService;
import com.bookstore.admin.services.InvoiceService;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;

@Controller
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private InvoiceDetailService invoiceDetailService;
	
	@GetMapping("/invoice")
	public String showInvoiceView(Model model) {
		List<Invoice> listInvoice = invoiceService.getAllInvoice();
		
		model.addAttribute("listInvoice", listInvoice);
		
		return "invoices";
	}
	
	@GetMapping("/invoice_detail")
	public String showInvoiceDetailView(@RequestParam("id") Integer id, Model model) {
		
		Invoice invoice = invoiceService.getInvoiceById(id);
		
		List<InvoiceDetail> invoiceDetail = invoiceDetailService.getInvoiceDetail(invoice);
		
		System.out.println("showInvoiceDetailView :: " + invoiceDetail.size());
		
		model.addAttribute("invoice", invoice);
		model.addAttribute("invoiceDetail", invoiceDetail);
		
		return "invoice_detail";
	}
	
	@GetMapping("/search_invoice")
	public String searchInvoiceView(Model model, @Param("code") String code) {
		if(code.length() < 10) {
			Invoice invoice = invoiceService.getInvoiceByCode(code);
			model.addAttribute("listInvoice", invoice);
		} else {
			Customer customer = customerService.getCustomerByPhoneNumber(code);
			List<Invoice> invoiceByCustomer = invoiceService.getInvoiceByCustomer(customer);
			model.addAttribute("listInvoice", invoiceByCustomer);
		}
		
		return "search_invoice";
	}
}
