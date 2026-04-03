package com.gautam.ecommerce_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gautam.ecommerce_backend.entity.User;
import com.gautam.ecommerce_backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}	
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
