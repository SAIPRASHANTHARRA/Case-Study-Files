package com.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagement.security.JWTService;
import com.usermanagement.entity.Guest;
import com.usermanagement.entity.Staff;
import com.usermanagement.entity.User;
import com.usermanagement.repository.GuestRepository;
import com.usermanagement.repository.StaffRepository;
import com.usermanagement.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		Guest guest = guestRepository.findGuestByEmail(user.getEmail());
		Staff staff= staffRepository.findStaffByEmail(user.getEmail());
		
		if (guest!=null) {
			user.setGuest(guest);
			user.setRole("GUEST");
			
		}
		else if (staff!=null) {
			user.setStaff(staff);
			user.setRole("STAFF");
		}
		else {
			user.setRole("MANAGER");
		}
		return userRepository.save(user);

	}

	public String verify(User user) {
		Authentication authentication = 
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
				
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		}
		return "fail";
	}
	
	

}
