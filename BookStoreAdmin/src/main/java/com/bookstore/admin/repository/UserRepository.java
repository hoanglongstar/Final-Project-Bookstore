package com.bookstore.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
	//full text search user
//	@Query(value = "SELECT * FROM users WHERE MATCH(username) against (?1)", nativeQuery = true)
//	public List<User> fullTextSearchUserByUsername(String username);
}
