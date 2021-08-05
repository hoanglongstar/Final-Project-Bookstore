package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.RoleRepository;
import com.bookstore.model.entities.Role;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
	
	public void save(Role role) {
		roleRepository.save(role);
	}
	
	public Role getRoleById(Integer id) {
		return roleRepository.getById(id);
	}
	
	public void deleteRoleById(Integer id) {
		roleRepository.deleteById(id);
	}
}
