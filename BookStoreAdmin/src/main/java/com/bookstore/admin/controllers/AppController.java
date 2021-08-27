package com.bookstore.admin.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.handler.AppConstant;
import com.bookstore.admin.helper.FileUploadHelper;
import com.bookstore.admin.services.CategoryService;
import com.bookstore.admin.services.ProductService;
import com.bookstore.admin.services.UserService;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Controller
public class AppController {
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private RoleService roleService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/login")
	public String showLoginView() {
		System.out.println("showLoginView");
		return "login";
	}

	@RequestMapping("/dashboard")
	public String showDashboardView(Model model) {
		return "dashboard";
	}
	
	@RequestMapping("/")
	public String showHomeView(Model model) {
		System.out.println("showHomeView");
		return "dashboard";
	}
	
	@GetMapping("/product")
	public String showBooksView(Model model) {
		List<Product> listProducts = productService.getAllProducts();
		List<Category> listCategories = categoryService.getAllCategory();
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("listCategories", listCategories);
		return "products";
	}
	
	@RequestMapping(value = {"/product/{pageNum}"})
	public String showBookPageView(Model model, @PathVariable(name = "pageNum") int pageNum) {
		Page<Product> pageProduct = productService.getProductWithPage(pageNum);
		List<Product> listProducts = pageProduct.getContent();
		List<Category> listCategories = categoryService.getAllCategory();
		
		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endcount = startCount + ProductService.PAGE_SIZE - 1;
		
		if(endcount > pageProduct.getTotalElements()) {
			endcount = pageProduct.getTotalElements();
		}

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endcount);
		model.addAttribute("listCategories", listCategories);
		
		return "products";
	}
	
	@GetMapping("/add_new_product")
	public String showShowAddNewProductView(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("listCategory",categoryService.getAllCategory());
		return "add_new_product";
	}
	
	@RequestMapping(value = "/add_new_product", method = RequestMethod.POST)
	public String createProduct(@Valid Product product, BindingResult bindingResult,@RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("listCategory",categoryService.getAllCategory());
			return "add_new_product";
		}
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!fileName.equals("")) {
			product.setPhoto(fileName);
			String uploadDir = AppConstant.PRODUCT_PHOTO_DIR + "/" + product.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		product.setEnabled(true);
		productService.saveProduct(product);
		
		return "redirect:/product/1";
	}
	
	@GetMapping("/edit_product/{code}")
	public String showEditProductView(@PathVariable(name = "code") String code, Model model) {
		
		Product product = productService.getProductByCode(code);
		
		model.addAttribute("product", product);
		model.addAttribute("listCategory", categoryService.getAllCategory());
		
		return "edit_product";
	}
	
	@RequestMapping(value = "/edit_product", method = RequestMethod.POST)
	public String saveProduct(@Valid Product product, BindingResult bindingResult, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("listCategory",categoryService.getAllCategory());
			return "/edit_product";
		}
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!fileName.equals("")) {
			product.setPhoto(fileName);
			String uploadDir = AppConstant.PRODUCT_PHOTO_DIR + "/" + product.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		product.setEnabled(true);
		productService.saveProduct(product);
		
		return "redirect:/product/1";
	}
	
	@GetMapping(value = "/search_product")
	public String searchProductView(Model model, @Param("name") String name) {
		List<Product> listProducts = productService.fullTextSearchProduct(name);
		model.addAttribute("listProducts", listProducts);
		return "search_product";
	}
	
	@GetMapping(value = "/productsByCategory")
	public String getProductsByCategory(Model model, @RequestParam(name = "categorySelected") String categorySelected) {
//		System.out.println("getProductsByCategory :: " + categorySelected);
		
		if(categorySelected.equals("All")) {
			return "redirect:/product/1";
		} 
		
		Category category = categoryService.getCategoryByName(categorySelected);
		
		List<Product> listProducts = productService.getListProductByCategory(category);
		List<Category> listCategories = categoryService.getAllCategory();

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("selectedCategory", category);
		
		return "search_product";
	}
	
	
	
	@RequestMapping("/delete_product/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		
		productService.deleteProductById(id);
		
		return "redirect:/product";
	}
	
	@RequestMapping("/login_error")
	public String showLoginErrorView(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("loginError", "Username or password is incorrect");
		System.out.println("login error");
		return "redirect:/login";
	}
	
	@RequestMapping("/signup")
	public String showSignUpView(Model model) {
		return "signup";
	}
	
	@GetMapping("/debug")
	public String showdebugView(Model model) {
		return "debug";
	}
	
	
}
