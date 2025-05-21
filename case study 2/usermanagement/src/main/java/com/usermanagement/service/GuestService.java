 package com.usermanagement.service;

import java.util.List;
import java.util.Optional;

import com.usermanagement.entity.Guest;
import com.usermanagement.exception.DuplicateGuestException;
import com.usermanagement.exception.DuplicateStaffException;
import com.usermanagement.exception.GuestNotFoundException;
import com.usermanagement.exception.InvalidInputException;
import com.usermanagement.repository.GuestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class GuestService {
	
	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	EmailService emailService;
	private static final Logger logger = LoggerFactory.getLogger(GuestService.class);
	
	public List<Guest> getAllGuests(){
		return guestRepository.findAll();
	}

	public String addGuest(Guest guest) {
		
		logger.info ("Adding new guest with emailId {} ",guest.getEmail());
		if(guestRepository.existsByEmail(guest.getEmail())) {
			logger.warn("Email cannot be duplicated for guest {}",guest.getName());
			throw new DuplicateGuestException("Guest with email id "+guest.getEmail()+" already exists");
		}
		if(guestRepository.existsByPhonenumber(guest.getPhonenumber())) {
			logger.warn("Phone number be duplicated for guest {}",guest.getName());
			throw new DuplicateGuestException("Guest with phone number "+guest.getPhonenumber()+" already exists");
		}
		
		if (guest.getName()== null ||guest.getName().isBlank()) {
			logger.warn("Invalid guest name {}",guest.getName());

			throw new InvalidInputException ("Guest name cannot be empty");
		}
		if(guest.getEmail()== null ||guest.getEmail().isBlank()) {
			logger.warn("Email cannot be empty for guest {}",guest.getName());

			throw new InvalidInputException("Guest email cannot be empty");
		}
		if(guest.getPhonenumber()== null ||guest.getPhonenumber().isBlank()) {
			logger.warn("Phone number cannot be empty for guest {}",guest.getName());
			throw new InvalidInputException("Guest phonenumber cannot be empty");
		}
		if(guest.getAddress()== null ||guest.getAddress().isBlank()) {
			logger.warn("Address cannot be empty for guest {}",guest.getName());
			throw new InvalidInputException("Guest address cannot be empty");
		}
		if(guest.getCompany()== null ||guest.getCompany().isBlank()) {
			logger.warn("Company cannot be empty for guest {}",guest.getName());

			throw new InvalidInputException("Guest company cannot be empty");
		}
		if(guest.getGender()== null ||guest.getGender().isBlank()) {
			logger.warn("Gender cannot be empty for guest {}",guest.getName());

			throw new InvalidInputException("Guest gender cannot be empty");
		}
		if(guest.getPhonenumber().length()!=10) {

			logger.warn("Invalid Guest Phonenumber for guest {}",guest.getName());
			
	    	throw new InvalidInputException("Length of Guest phonenumber cannot be lesser than or greater than 10");
	    }
	    if (!guest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
	    	logger.warn("Invalid email format for guest {} ",guest.getName());
	    	throw new InvalidInputException("Invalid Email format");
	    }
		guestRepository.save(guest);
		String subject="Welcome to OHMS";
		String body = "Hi " + guest.getName() + ",\n\nThanks for registering with OHMS(Online Hotel Management System). We're excited to deliver our Hotel Services with you!";
	    emailService.sendEmail(guest.getEmail(), subject, body);

		return "guest added Successfully";
	}
	

	public Guest getGuestById(Long guestId){
		return guestRepository.findById(guestId)
				.orElseThrow(()-> new GuestNotFoundException("Guest not found with guestID: "+guestId) );
	}
	public String updateGuest( Long guestId,Guest updatedGuest) {
		Guest existingGuest= guestRepository.findById(guestId)
				.orElseThrow(() -> new GuestNotFoundException("Guest not found with guestID: " + guestId));
	
		if(!existingGuest.getEmail().equals(updatedGuest.getEmail())&& guestRepository.existsByEmail(updatedGuest.getEmail())) {
			logger.warn("Email cannot be duplicated for guest {}",updatedGuest.getName());
			throw new DuplicateGuestException("Guest with email id "+updatedGuest.getEmail()+" already exists");
		}
		if(!existingGuest.getPhonenumber().equals(updatedGuest.getPhonenumber())&& guestRepository.existsByPhonenumber(updatedGuest.getPhonenumber())) {
			logger.warn("Phone number cannot be duplicated for guest {}",updatedGuest.getName());
			throw new DuplicateGuestException("Guest with phone number "+updatedGuest.getPhonenumber()+" already exists");
		}
		    if (updatedGuest.getName() == null || updatedGuest.getName().isBlank()) {
		    	logger.warn("Name cannot be empty for guest {}",existingGuest.getEmail());
		        throw new InvalidInputException("Guest name cannot be empty");
		    }
		    if (updatedGuest.getEmail() == null || updatedGuest.getEmail().isBlank()) {
		    	logger.warn("Email cannot be empty for guest {}",updatedGuest.getName());
		        throw new InvalidInputException("Guest email cannot be empty");
		    }
		    if (updatedGuest.getPhonenumber() == null || updatedGuest.getPhonenumber().isBlank()) {
		    	logger.warn("Phone number cannot be empty for guest {}",updatedGuest.getName());
		        throw new InvalidInputException("Guest phone number cannot be empty");
		    }
		    if (updatedGuest.getAddress() == null || updatedGuest.getAddress().isBlank()) {
		    	logger.warn("Address cannot be empty for guest {}",updatedGuest.getName());
		        throw new InvalidInputException("Guest address cannot be empty");
		    }
		    if (updatedGuest.getCompany() == null || updatedGuest.getCompany().isBlank()) {
		    	logger.warn("Company cannot be empty for guest {}",updatedGuest.getName());
		        throw new InvalidInputException("Guest company cannot be empty");
		    }
		    if (updatedGuest.getGender() == null || updatedGuest.getGender().isBlank()) {
		    	logger.warn("Gender cannot be empty for guest {}",updatedGuest.getName());
		        throw new InvalidInputException("Guest gender cannot be empty");
		    }
		    
		    if(updatedGuest.getPhonenumber().length()!=10) {
		    	logger.warn("Invalid phone number for guest {} ",updatedGuest.getName());
		    	throw new InvalidInputException("Length of Guest phonenumber cannot be lesser than or greater than 10");
		    }
		    if (!updatedGuest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
		    	logger.warn("Invalid email format for guest {} ",updatedGuest.getName());
		    	throw new InvalidInputException("Invalid Email format");
		    }
			existingGuest.setAddress(updatedGuest.getAddress());
			existingGuest.setName(updatedGuest.getName());
			existingGuest.setCompany(updatedGuest.getCompany());
			existingGuest.setEmail(updatedGuest.getEmail());
			existingGuest.setGender(updatedGuest.getGender());
			existingGuest.setPhonenumber(updatedGuest.getPhonenumber());
		guestRepository.save(existingGuest);
		return "Guest updated successfully";
    
	}
	

}