package com.bookstore.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.client.services.CategoryService;
import com.bookstore.client.services.ProductService;
import com.bookstore.client.shopcart.CartInfo;
import com.bookstore.client.shopcart.CartLineInfo;
import com.bookstore.client.shopcart.ShopCartSessionUtil;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"/productlist"})
	public String showProductView(Model model) {	
//		System.out.println("sp:*************************************************** " );
//
//		List<Product> products = productService.getAllProduct();
//		model.addAttribute("products", products);
		return showProductPageView(model, 1);
//		return "product-list";
		

	}
	
	@RequestMapping(value = {"/productlist/{pageNum}"})
	public String showProductPageView(Model model, @PathVariable(name = "pageNum") int pageNum) {
		
		Page<Product> pageProduct = productService.getProductsWithPage(pageNum);
		List<Product> products = pageProduct.getContent();
		
		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endCount = startCount + ProductService.PAGE_SIZE - 1;
		
		if( endCount > pageProduct.getTotalElements()) {
			endCount = pageProduct.getTotalElements();
		}
//		for(Product product : products) {
//			System.out.println("Sp:" + product.getCode());
//		}
		
		model.addAttribute("products", products);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		return "product";
	}
	
	@GetMapping(value = {"/searchproducts"})
	public String showSearchProduct(Model model, @Param(value = "value") String value) {
		
//		List<Product> listProduct = productService.getProductByName(value);
		
		List<Product> listProduct = productService.fullTextSearchProductByName(value);
		
		System.out.println("Danh sach san pham sau khi search: " + listProduct.size());
		
		
		System.out.println(value);
		
		
		model.addAttribute("products", listProduct);
		
		return "search_product";
	}
	
	@GetMapping(value = {"/categoryproducts"})
	public String showCategoryProduct(Model model) {
		List<Category> listCategory = categoryService.getRootCategory();
		model.addAttribute("categories", listCategory);
		
		return "category";
	}
	
	@GetMapping(value = {"/categoryproducts/{id}"} )
	public String showCategoryProductID(Model model , @PathVariable ("id") Integer id) {
		Category category = categoryService.getById(id);
		
		List<Category> listcategory =  new ArrayList<>();
		listcategory.addAll(category.getSubCategory());
		
		List<Category> listParent = categoryService.getListParent(category);
		model.addAttribute("parents", listParent);
		model.addAttribute("categories", listcategory);
		
		List<Product> listProduct = productService.getProductCategory(category);
		model.addAttribute("products", listProduct);
		
		return "category";
	}
	
//	@RequestMapping("/addtocart")
//	public String byProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) {
//		
//		Product product = productService.getByCode(code);
//		System.out.println("code: " + code);
//
//		
//		if (product != null) {
//			CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
//			cartInfo.addProduct(product, 1);
//			System.out.println("code: " + product);
//		}
//		
//		return "redirect:/shopping_cart";
//	}
//	
//	@RequestMapping("/shopping_cart")
//	public String showCartView(HttpServletRequest request, Model model) {
//		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
//		
////		for(CartLineInfo cartLine : cartInfo.getCartLines()) {
////			System.out.println("ten san pham : " + cartLine.getProduct().getName());
////			System.out.println("so luong : " + cartLine.getQuantity());
////			System.out.println("Unit price : " + cartLine.getUnitPrice());
////		}
//		
//		model.addAttribute("cartInfo", cartInfo);
//		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
//		
//		return "cart";
//	}
//	
//	@PostMapping("/update_cart")
//	public String updateCart(@ModelAttribute("cartInfo") CartInfo cartInfo ,HttpServletRequest request, Model model) {
////		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
//		for(CartLineInfo cartLine : cartInfo.getCartLines()) {
//			System.out.println("ten san pham : " + cartLine.getProduct().getName());
//			System.out.println("so luong : " + cartLine.getQuantity());
//			System.out.println("Unit price : " + cartLine.getUnitPrice());
//		}
//		System.out.println("updateCart" + cartInfo.getCartLines().size());
//		return "redirect:/shopping_cart";
//	}
}
