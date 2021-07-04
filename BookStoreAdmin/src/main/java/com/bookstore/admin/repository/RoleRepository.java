package com.bookstore.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
