package com.gautam.ecommerce_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gautam.ecommerce_backend.dto.UserRequestDTO;
import com.gautam.ecommerce_backend.dto.UserResponseDTO;
import com.gautam.ecommerce_backend.entity.User;
import com.gautam.ecommerce_backend.exception.UserAlreadyExistsException;
import com.gautam.ecommerce_backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserResponseDTO saveUser(UserRequestDTO requestDTO) {
		
	    if (userRepository.existsByEmail(requestDTO.getEmail())) {
	        throw new UserAlreadyExistsException("Email already registered");
	    }
		
		User user = new User();
		user.setEmail(requestDTO.getEmail());
		
		String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());
		user.setPassword(hashedPassword);
		
		User savedUser = userRepository.save(user);
		
		return new UserResponseDTO(savedUser.getId(), savedUser.getEmail());
	}
	
	public List<UserResponseDTO> getAllUsers() {
		List <User> users = userRepository.findAll();
		
		return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getEmail())).toList();
	}
}
