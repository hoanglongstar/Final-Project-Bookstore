package com.bookstore.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p WHERE p.code = :code")
	public Product getByCode(@Param("code") String code);
	
	//full text search product
	@Query(value = "SELECT * FROM products WHERE MATCH(name) against (?1)", nativeQuery = true)
	public List<Product> fullTextSearchUserByUsername(String name);
}
