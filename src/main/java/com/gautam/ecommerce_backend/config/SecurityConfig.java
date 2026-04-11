package com.gautam.ecommerce_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gautam.ecommerce_backend.security.CustomAccessDeniedHandler;
import com.gautam.ecommerce_backend.security.CustomAuthenticationEntryPoint;
import com.gautam.ecommerce_backend.security.JwtFilter;
import com.gautam.ecommerce_backend.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) 
    		throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
            	    .requestMatchers("/api/users/login").permitAll()
            	    .requestMatchers("/api/users").hasRole("ADMIN")
            	    .anyRequest().authenticated()
            	)
            .exceptionHandling(ex -> ex
            		.authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
}