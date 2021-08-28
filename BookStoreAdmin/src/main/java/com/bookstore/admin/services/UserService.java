package com.bookstore.admin.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.UserRepository;
import com.bookstore.model.entities.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User getUserById(Integer id) {
		return userRepository.getById(id);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}
	
	public void saveUser(User user) {
		
		Pattern bcryptPasswordPattern = Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");  
		
		if(!bcryptPasswordPattern.matcher(user.getPassword()).matches()) {
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			
			user.setPassword(encodePassword);
		}
		
		
		userRepository.save(user);
	}
	
	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
	}
	
//	public List<User> fullTextSearchUserByUsername(String username) {
//		return userRepository.fullTextSearchUserByUsername(username);
//	}
}
