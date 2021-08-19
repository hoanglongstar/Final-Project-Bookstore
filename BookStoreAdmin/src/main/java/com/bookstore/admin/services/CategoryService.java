package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.CategoryRepository;
import com.bookstore.model.entities.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	public void deleteCategoryById(Integer id) {
		categoryRepository.deleteById(id);
	}
	
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public Category getCategoryById(Integer id) {
		Category category = categoryRepository.getById(id);
		return category;
	}
}
