package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	//User findByUsermail(String usermail);
	
	User findByUsername(String username);

}
