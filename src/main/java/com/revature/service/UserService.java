package com.revature.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDao;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;

@Service // telling spring (with a stereotype annotation) what type of job this bean does
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	// findAll
	public Set<User> findAll() {
		return userDao.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	// findByUsername
	public User findByUsername(String username) {
		
		// we're leveraging the Optional class methods. In the case that we return a null object, we can throw an exception
		return userDao.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("No User found with username " + username));
	}
	
	// findById
	public User findById(int id) {
		
		return userDao.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No User found with username " + id));
	}
	
	public User insert(User u) {
		
		// add some logic here to make sure user is not null
		
		return userDao.save(u);
	}

}
