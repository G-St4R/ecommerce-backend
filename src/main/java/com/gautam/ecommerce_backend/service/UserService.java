package com.gautam.ecommerce_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gautam.ecommerce_backend.dto.UserRequestDTO;
import com.gautam.ecommerce_backend.dto.UserResponseDTO;
import com.gautam.ecommerce_backend.entity.User;
import com.gautam.ecommerce_backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserResponseDTO saveUser(UserRequestDTO requestDTO) {
		
		User user = new User();
		user.setEmail(requestDTO.getEmail());
		user.setPassword(requestDTO.getPassword());
		
		User savedUser = userRepository.save(user);
		
		return new UserResponseDTO(savedUser.getId(), savedUser.getEmail());
	}
	
	public List<UserResponseDTO> getAllUsers() {
		List <User> users = userRepository.findAll();
		
		return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getEmail())).toList();
	}
}
