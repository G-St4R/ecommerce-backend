package com.gautam.ecommerce_backend.entity;


import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	
	private String password;
	
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
	    return id;
	}

	public String getEmail() {
	    return email;
	}

	public String getPassword() {
	    return password;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public void setPassword(String password) {
	    this.password = password;
	}
}
