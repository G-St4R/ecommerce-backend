package com.gautam.ecommerce_backend.entity;


import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	private String email;
	private String password;
}
