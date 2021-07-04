package com.bookstore.admin.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bookstore.admin.handler.AppConstant;
import com.bookstore.admin.helper.FileUploadHelper;
import com.bookstore.admin.services.ProductService;
import com.bookstore.admin.services.RoleService;
import com.bookstore.admin.services.UserService;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;
import com.bookstore.model.entities.Role;
import com.bookstore.model.entities.User;
import com.bookstore.model.formdata.ProductData;
import com.bookstore.model.formdata.UserData;

@Controller
public class AppController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/login")
	public String showLoginView() {
		System.out.println("showLoginView");
		return "login";
	}

	@RequestMapping("/dashboard")
	public String showDashboardView(Model model) {
		return "dashboard";
	}
	
//	@RequestMapping("/")
//	public String showHomeView(Model model) {
//		System.out.println("showHomeView");
//		return "dashboard";
//	}
	
	@GetMapping("/user")
	public String showUsersView(Model model) {
		List<User> listUsers = userService.getAllUsers();
		
		List<UserData> copyListUser = new ArrayList<UserData>();
		
		for(User user : listUsers) {
			copyListUser.add(UserData.copyValueFromUserEntity(user));
		}

		model.addAttribute("listUsers", copyListUser);
		return "users";
	}
	
	@GetMapping("/product")
	public String showBooksView(Model model) {
		List<Product> listProducts = productService.getAllProducts();
		model.addAttribute("listProducts",listProducts);
		return "products";
	}
	
	@GetMapping("/create_new_user")
	public String showCreateNewUserView(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", roleService.getAllRoles());
		return "create_user";
	}
	
	@GetMapping("/add_new_product")
	public String showShowAddNewProductView(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "add_product";
	}
	
	@RequestMapping(value = "/save_user", method = RequestMethod.POST)
	public String saveUser(@RequestParam("fileImage") MultipartFile multipartFile ,@ModelAttribute("user") User user) {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		user.setAvatar(fileName);
		user.setEnabled(true);
		user.setPassword("123456");
		userService.saveUser(user);
		
		String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + user.getId();
		
		try {
			FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/user";
	}
	
	@RequestMapping(value = "/save_new_product", method = RequestMethod.POST)
	public String saveNewProduct(@ModelAttribute("product") Product product) {

		Category category = new Category();
		category.setId(2);
		
		product.setCategory(category);
		product.setEnabled(true);
		productService.saveProduct(product);
		
		return "redirect:/product";
	}
	
	@RequestMapping(value = "/save_product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") ProductData productData) {

		Product entity = productService.getProductByCode(productData.getCode());
		
		if(entity != null) {
			entity.updateFormData(productData);
			Category category = new Category();
			category.setId(2);
			
			entity.setCategory(category);
			productService.saveProduct(entity);
		}

		return "redirect:/product";
	}
	
	@RequestMapping("/edit_user/{id}")
	public ModelAndView showEditUserView(@PathVariable(name = "id") Integer id) {
		
		ModelAndView modelAndView = new ModelAndView("edit_user");
		
		User user = userService.getUserById(id);
		
		List<Role> listRoles = roleService.getAllRoles();
		
		modelAndView.addObject(user);
		modelAndView.addObject(listRoles);

		return modelAndView;
	}
	
	@RequestMapping("/edit_product/{code}")
	public String showEditProductView(@PathVariable(name = "code") String code, Model model) {
		
		Product entity = productService.getProductByCode(code);
		
		ProductData productData = ProductData.copyValueFormEntity(entity);
		
		model.addAttribute("product", productData);
		
		return "edit_product";
	}
	
	@RequestMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id) {
		
		userService.deleteUserById(id);
		
		return "redirect:/user";
	}
	
	@RequestMapping("/delete_product/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		
		productService.deleteProductById(id);
		
		return "redirect:/product";
	}
	
	@RequestMapping("/login_error")
	public String showLoginErrorView() {
		return "login_error";
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
