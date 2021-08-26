package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.ProductRepository;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public static int PAGE_SIZE = 1;
	
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
	
	public List<Product> getListProductByCategory(Category category){
		return productRepository.getByCategory(category);
	}
	
	public Page<Product> getProductWithPage(int pageNum){
		Pageable pageable;
		
		if(pageNum >= 1) {
			pageable = PageRequest.of(pageNum - 1, ProductService.PAGE_SIZE);
		} else {
			pageable = PageRequest.of(0, ProductService.PAGE_SIZE);
		}
		
		return productRepository.findAll(pageable);
	}
}
