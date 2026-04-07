package com.gautam.ecommerce_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.ecommerce_backend.dto.LoginRequestDTO;
import com.gautam.ecommerce_backend.dto.UserRequestDTO;
import com.gautam.ecommerce_backend.dto.UserResponseDTO;
import com.gautam.ecommerce_backend.entity.User;
import com.gautam.ecommerce_backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
		return userService.saveUser(requestDTO);
	}
	
	
	@GetMapping
	public List<UserResponseDTO> getAllUsers() {
		return userService.getAllUsers();
	}
	
	
	@PostMapping("/login")
	public Map<String, String> loginUser(@Valid @RequestBody LoginRequestDTO requestDTO) {

	    String token = userService.loginUser(requestDTO);

	    Map<String, String> response = new HashMap<>();
	    response.put("token", token);

	    return response;
	}

}
