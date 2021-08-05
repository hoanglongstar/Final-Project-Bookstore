package com.bookstore.admin.controllers;

import java.io.IOException;
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
import com.bookstore.admin.services.CategoryService;
import com.bookstore.admin.services.ProductService;
import com.bookstore.admin.services.UserService;
import com.bookstore.model.entities.Product;
import com.bookstore.model.entities.User;
import com.bookstore.model.formdata.UserData;

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
		model.addAttribute("listProducts",listProducts);
		return "products";
	}
	
	@GetMapping("/add_new_product")
	public String showShowAddNewProductView(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("listCategory",categoryService.getAllCategory());
		return "add_product";
	}
	
	@RequestMapping(value = "/save_new_user", method = RequestMethod.GET)
	public String saveUser(@ModelAttribute("user") UserData userData) {
		
//		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//		
//		User user = new User();
//
//		if(!fileName.equals("")) {
//			userData.setAvatar(fileName);
//			String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + userData.getId();
//			
//			try {
//				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		userData.setEnabled(true);
//		
//		user = userData.updateUserFormData();
//		
//		userService.saveUser(user);

		return "redirect:/user";
	}
	
	@RequestMapping(value = "/save_product", method = RequestMethod.POST)
	public String saveProduct(@RequestParam("fileImage") MultipartFile multipartFile ,@ModelAttribute("product") Product product) {

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
		
		return "redirect:/product";
	}
	
//	@RequestMapping("/edit_user/{id}")
//	public ModelAndView showEditUserView(@PathVariable(name = "id") Integer id) {
//		
//		ModelAndView modelAndView = new ModelAndView("edit_user");
//		
//		User user = userService.getUserById(id);
//				
//		System.out.println("Edit User View: " + user.getAvatar());
//		
//		List<Role> listRoles = roleService.getAllRoles();
//		
//		modelAndView.addObject("user",user);
//		modelAndView.addObject("listRoles",listRoles);
//
//		return modelAndView;
//	}
	
//	@RequestMapping("/change_password")
//	public ModelAndView showChangePasswordView() {
//		ModelAndView modelAndView = new ModelAndView("change_password");
//		
////		User user = userService.getUserById(id);
//		
////		modelAndView.addObject("user",user);
//		
//		return modelAndView;
//	}
	
//	@RequestMapping(value = "/save_password", method = RequestMethod.POST)
//	public String savePassword(@ModelAttribute("user") User user) {
//		//Change password code
//		return null;
//	}
	
	@RequestMapping("/edit_product/{code}")
	public String showEditProductView(@PathVariable(name = "code") String code, Model model) {
		
		Product product = productService.getProductByCode(code);
		
		model.addAttribute("product", product);
		model.addAttribute("listCategory", categoryService.getAllCategory());
		
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
