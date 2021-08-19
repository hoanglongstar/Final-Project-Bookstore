package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.ProductRepository;
import com.bookstore.model.entities.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product getProductByCode(String code) {
		return productRepository.getByCode(code);
	}
	
	public void saveProduct(Product product) {
		productRepository.save(product);
	}
	
	public void deleteProductById(Integer id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> fullTextSearchProduct(String name){
		return productRepository.fullTextSearchUserByUsername(name);
	}
}
