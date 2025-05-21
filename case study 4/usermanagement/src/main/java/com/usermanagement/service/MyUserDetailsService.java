
package com.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.User;
import com.usermanagement.entity.UserPrincipal;
import com.usermanagement.repository.GuestRepository;
import com.usermanagement.repository.StaffRepository;
import com.usermanagement.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	StaffRepository staffRepository;
	

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	User user = userRepository.findByEmail(email);
	if (user==null) {
		throw new UsernameNotFoundException("User not found");
	}
	
	if(guestRepository.findGuestByEmail(email)!=null) {
		user.setRole("GUEST");
	}
	
	else if (staffRepository.findStaffByEmail(email)!=null) {
		user.setRole("STAFF");
	}
	else {
		user.setRole("MANAGER");
}
	
	
	return new UserPrincipal(user);

}
}

