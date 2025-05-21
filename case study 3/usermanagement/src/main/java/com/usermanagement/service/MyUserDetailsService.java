
package com.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.User;
import com.usermanagement.entity.UserPrincipal;
import com.usermanagement.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	User user = userRepository.findByUsername(username);
	if (user==null) {
		System.out.println("User Not Found");
		throw new UsernameNotFoundException("User not found");
	}
		return new UserPrincipal(user);
	}
	
	
	
	

}        

