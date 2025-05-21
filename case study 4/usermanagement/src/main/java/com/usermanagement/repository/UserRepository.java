package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.Guest;
import com.usermanagement.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	User findByEmail(String email);
	
	//User findByUsername(String username);
	

}
