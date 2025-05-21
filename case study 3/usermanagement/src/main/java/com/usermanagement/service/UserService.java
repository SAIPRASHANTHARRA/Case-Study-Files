package com.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.User;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.security.JWTService;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	AuthenticationManager authManager;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	public String verify(User user) {
		Authentication authentication = 
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
				
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		}
		return "fail";
	}
	
	

}
