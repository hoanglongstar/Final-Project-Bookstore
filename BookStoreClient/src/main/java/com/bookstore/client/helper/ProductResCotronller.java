package com.bookstore.client.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.client.repository.ProductReponsitory;
import com.bookstore.model.entities.Product;
import com.bookstore.model.formdata.ProductAutoComplete;

@RestController
public class ProductResCotronller {
	
	@Autowired
	private ProductReponsitory repositoryProduct;

	@GetMapping("api/products")
	public List<Product> greeting(@RequestParam(value = "name", defaultValue = "") String name) {
		List<Product> listProducts = new ArrayList<>();
		if(name.equals("")) {
			listProducts = repositoryProduct.findAll();		
		}
		else
		{
			listProducts = repositoryProduct.fullTextSearchProductByName(name);
		}
		
		return listProducts;
	}
	
	@GetMapping("api/products/autocomplete")
	public List<ProductAutoComplete> productAutoComplete(@RequestParam(value = "name", defaultValue = "") String name){
		List<Product> listProducts = new ArrayList<>();
		List<ProductAutoComplete> listAuto = new ArrayList<>();
		if(!name.equals("")) {			
			listProducts = repositoryProduct.autoCompleteProductByName(name);
		}
		
		for(Product product:listProducts) {
			ProductAutoComplete newProduct = new ProductAutoComplete();
			newProduct.setId(product.getId());
			newProduct.setName(product.getName());
			newProduct.setPhoto("https://cdn.nguyenkimmall.com/images/thumbnails/600/336/detailed/741/10049758-laptop-asus-x415e-i3-1115g4-14-inch-x415ea-ek047t-1.jpg");
			listAuto.add(newProduct);
		}
		return listAuto;
	}
}
