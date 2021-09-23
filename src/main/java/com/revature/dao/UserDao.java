package com.revature.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;

@Repository
@Transactional					// JpaRepository allows us to do CRUD methods and many more
public interface UserDao extends JpaRepository<User, Integer>{

	/**
	 * Property Expressions (look into the Spring documentation about what you can do with these)
	 * Property Expressions infer SQL statements on entities based on the DIRECt
	 * properties of the entity being managed
	 */
	
	// This will automatically generate a SQL statement that uses DQL (with a WHERE clause)
	// to target the User table and search for the record with the lastName passed
	public User findByLastName(String lastName);
	
	public Optional<User> findByUsername(String username);
	
	public User findByFirstNameIgnoreCase(String firstName);
	
	@Query(value="FROM User WHERE email LIKE %:substring")
	public User findByEmailContains(String substring); // if we pass in ith@mail.com -> return johnsmith@mail.com
	
	// Remember that all the basic CRUD methods already exist like findById(), update()... etc.

}
