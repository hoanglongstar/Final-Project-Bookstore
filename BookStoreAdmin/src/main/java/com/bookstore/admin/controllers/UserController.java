package com.bookstore.admin.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.handler.AppConstant;
import com.bookstore.admin.helper.FileUploadHelper;
import com.bookstore.admin.helper.PasswordManager;
import com.bookstore.admin.services.RoleService;
import com.bookstore.admin.services.UserService;
import com.bookstore.model.entities.Role;
import com.bookstore.model.entities.User;
import com.bookstore.model.formdata.UserData;
import com.bookstore.model.formdata.UserDataForConfirmPassword;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/user")
	public String showUsersView(Model model) {
		List<User> listUsers = userService.getAllUsers();
		
		List<UserData> copyListUser = new ArrayList<UserData>();
		
		for(User user : listUsers) {
			copyListUser.add(user.copyValueFromUserEntity());
		}

		model.addAttribute("listUsers", copyListUser);
		return "users";
	}
	
	//*************************
	//*    Create New User    *
	//*************************
	
	@GetMapping("/create_user")
	public String showCreateNewUserView(Model model) {
		User user = new User();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", roleService.getAllRoles());
		return "create_user";
	}
	
	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public String checkNewUserInfo(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("listRoles", roleService.getAllRoles());
			return "create_user";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());


		if(!fileName.equals("")) {
			user.setAvatar(fileName);
			String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + user.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		user.setEnabled(true);
		
		System.out.println(user.getRoles().size());
		userService.saveUser(user);
		return "redirect:/user";
	}
	
	//*************************
	//*       Edit User       *
	//*************************
	
	@RequestMapping(value = "/edit_user/{id}", method = RequestMethod.GET)
	public ModelAndView showEditUserView(@PathVariable(name = "id") Integer id) {
		
//		UserData userData = new UserData();
		
		ModelAndView modelAndView = new ModelAndView("edit_user");
		
		User user = userService.getUserById(id);
		
//		UserData userData = user.copyValueFromUserEntity();
				
//		System.out.println("Edit User View: " + user.getAvatar());
		
		List<Role> listRoles = roleService.getAllRoles();
		
		modelAndView.addObject("user",user);
		modelAndView.addObject("listRoles",listRoles);

		return modelAndView;
	}
	
	@RequestMapping(value = "/edit_user", method = RequestMethod.POST)
	public String checkExistUserInfo(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Model model) {

//		String path = "";
//		User user = new User();
//		System.out.println("checkExistUserInfo");
		
		System.out.println("checkExistUserInfo :: " + user.getDateOfBirth());
		
		if(bindingResult.hasErrors()) {
			System.out.println("---------------------------------------"+user.getDateOfBirth());
			System.out.println("---------------------------------------"+bindingResult.hasErrors());
			System.out.println("---------------------------------------"+bindingResult.toString());
			System.out.println((Date) (user.getDateOfBirth()));
			List<Role> listRoles = roleService.getAllRoles();
			model.addAttribute("listRoles",listRoles);
			
//			System.out.println("checkExistUserInfo :: " + user.getPhotoPath());
//			System.out.println("EDIT USER: " + user.getId());
//			model.addAttribute("listRoles", roleService.getAllRoles());
//			path = "/edit_user/" + user.getId();
			System.out.println("has errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			
			return "/edit_user";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());


		if(!fileName.equals("")) {
			user.setAvatar(fileName);
			String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + user.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		Date sqlDate = (Date) (user.getDateOfBirth());
//		user.setDateOfBirth(sqlDate);
//		user = user.updateUserFormData();

		user.setEnabled(true);
		
//		System.out.println(userData.getRoles().size());
		userService.saveUser(user);
		return "redirect:/user";
	}
	
	@RequestMapping(value = "/confirm_old_password", method = RequestMethod.GET)
	public String showConfirOldPasswordView(Model model) {
		
		UserDataForConfirmPassword userData = new UserDataForConfirmPassword();
		
		model.addAttribute("userData", userData);
		
		return "confirm_old_password";
	}
	
	@RequestMapping(value = "/confirm_old_password", method = RequestMethod.POST)
	public String checkUserPassword(@Valid UserDataForConfirmPassword userData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		Boolean error = false;
		
		if(bindingResult.hasErrors()) {
			System.out.println("not enough charater");
			return "/confirm_old_password";
		}
		
		User oldUser = getCurrentUser();
		String oldPassword = oldUser.getPassword();

		if(!PasswordManager.checkPassword(userData.getPassword(), oldPassword)) {
			redirectAttributes.addFlashAttribute("error_wrong_password", "The password you entered is incorrect.");
			error = true;
		}
		
		if(error) {
			return "redirect:/confirm_old_password";
		}
		
		return "redirect:/change_password";
	}
	
	@RequestMapping(value = "/change_password", method = RequestMethod.GET)
	public String showChangePasswordView(Model model) {
		
		UserData userData = new UserData();
		
		model.addAttribute("userData", userData);
		
		return "change_password";
	}
	
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public String checkNewPassword(@Valid UserData userData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		Boolean error = false;

		if(bindingResult.hasErrors()) {
			System.out.println("not enough charater");
			return "/change_password";
		}
		
		User oldUser = getCurrentUser();

		if(!((userData.getPassword()).equals(userData.getPasswordRetype()))) {
			redirectAttributes.addFlashAttribute("error_match_password", "The password you entered is not matched.");
			error = true;
		}
		
		if(PasswordManager.checkPassword(userData.getPassword(), oldUser.getPassword())) {
			redirectAttributes.addFlashAttribute("error_match_old_password", "Please use a new password");
			error = true;
		}
		
		if(error) {
			return "redirect:/change_password";
		}
		
		oldUser.setPassword(PasswordManager.getBCrypPassword(userData.getPasswordRetype()));
		
		userService.saveUser(oldUser);
		
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value = "/update_profile", method = RequestMethod.GET)
	public String showProfileView(Model model) {
		
		User user = getCurrentUser();
		
		model.addAttribute("user", user);
		
		return "profile";
	}
	
	@RequestMapping(value = "/update_profile", method = RequestMethod.POST)
	public String checkUserInfo(@RequestParam("fileImage") MultipartFile multipartFile, @ModelAttribute("user") User user) {
		
		System.out.println("checkUserInfo :: " + user.getPhotoPath());
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!fileName.equals("")) {
			user.setAvatar(fileName);
			String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + user.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		user.setEnabled(true);
		
		userService.saveUser(user);
		return "redirect:/user";
	}
	
	@Transient
	public User getCurrentUser() {
		String username = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			 username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
			
		User currentUser = userService.getUserByUsername(username);

		return currentUser;
	}
}
