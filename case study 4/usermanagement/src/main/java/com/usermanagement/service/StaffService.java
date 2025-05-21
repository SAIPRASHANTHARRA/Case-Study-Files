package com.usermanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.usermanagement.entity.Staff;
import com.usermanagement.exception.DuplicateGuestException;
import com.usermanagement.exception.DuplicateStaffException;
import com.usermanagement.exception.GuestNotFoundException;
import com.usermanagement.exception.InvalidInputException;
import com.usermanagement.exception.InvalidStaffInputException;
import com.usermanagement.exception.StaffNotFoundException;
import com.usermanagement.repository.StaffRepository;

@Service
public class StaffService {
	
	private static final Logger logger = LoggerFactory.getLogger(StaffService.class);
	@Autowired
	StaffRepository staffRepository;
	
	public String addStaff(Staff staff) {
		if(staffRepository.existsByEmail(staff.getEmail())) {
			logger.warn("Email cannot be duplicated for staff {}",staff.getName());
			throw new DuplicateStaffException("Staff with email "+staff.getEmail()+" already exists");
		}
		if(staffRepository.existsByPhoneno(staff.getPhoneno())) {
			logger.warn("Phone number already exists {}",staff.getPhoneno());
			throw new DuplicateStaffException("Staff with phoneno "+staff.getPhoneno()+" already exists");
		}
		if(staffRepository.existsByNic(staff.getNic())) {
			logger.warn("Nic already exists {}",staff.getNic());

			throw new DuplicateStaffException("Staff with Nic "+staff.getNic()+" already exists");
		}

		
		if (staff.getName()== null ||staff.getName().isBlank()) {
			logger.warn("Invalid staff name {}",staff.getName());
			throw new InvalidStaffInputException ("Staff name cannot be empty");
		}
		if(staff.getEmail()== null ||staff.getEmail().isBlank()) {
			logger.warn("Email cannot be empty for staff {}",staff.getName());
			throw new InvalidStaffInputException("Staff email cannot be empty");
		}
		if(staff.getNic()== null ) {
			logger.warn("NIC cannot be empty for staff {}",staff.getName());
			throw new InvalidStaffInputException("Staff nic cannot be empty");
		}
		if(staff.getAddress()== null ||staff.getAddress().isBlank()) {
			logger.warn("Address cannot be empty for staff {}",staff.getAddress());
			throw new InvalidStaffInputException("Staff address cannot be empty");
		}
		
		if(staff.getOccupation()== null ||staff.getOccupation().isBlank()) {
			logger.warn("Occupation cannot be empty for staff {}",staff.getOccupation());
			throw new InvalidStaffInputException("Staff occupation cannot be empty");
		}
		if (staff.getAge()<18 ) {
			logger.warn("Age cannot be less than 18 for staff {}",staff.getName());
			throw new InvalidStaffInputException("Staff Age cannot be less than 18");
		} 
		 if(staff.getPhoneno().length()!=10) {
			 logger.warn("Invalid staff Phonenumber for staff {}",staff.getName());
		    	throw new InvalidInputException("Length of Staff phonenumber cannot be lesser than or greater than 10");
		    }
		    if (!staff.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
		    	logger.warn("Invalid Staff Email format for staff {}",staff.getName());
		    	throw new InvalidInputException("Invalid Email format");
		    }
		 staffRepository.save(staff);
		 return "Staff added successfully";
	}
	
	public List<Staff> getAllStaff(){
		return staffRepository.findAll();
	}

	public String updateStaff(Long code,Staff updatedStaff) {
		Staff existingStaff = staffRepository.findById(code).orElseThrow(()->new StaffNotFoundException("Staff not found with code:"+code));
		
		if(!existingStaff.getEmail().equals(updatedStaff.getEmail())&& staffRepository.existsByEmail(updatedStaff.getEmail())) {
			logger.warn("Email already exists for {}",updatedStaff.getName());
			throw new DuplicateStaffException("Staff with email id "+updatedStaff.getEmail()+" already exists");
		}
		if(!existingStaff.getNic().equals(existingStaff.getNic())&& staffRepository.existsByNic(updatedStaff.getNic())) {
			logger.warn("Nic already exists for {}",updatedStaff.getName());
			throw new DuplicateStaffException("Staff with nic "+updatedStaff.getNic()+" already exists");
		}
		if(!existingStaff.getPhoneno().equals(existingStaff.getPhoneno())&& staffRepository.existsByPhoneno(updatedStaff.getPhoneno())) {
			logger.warn("Phone number already exists for {}",updatedStaff.getName());
			throw new DuplicateStaffException("Staff with phone number "+updatedStaff.getPhoneno()+" already exists");
		}
		
		if (updatedStaff.getName()== null ||updatedStaff.getName().isBlank()) {
			logger.warn("Name cannot be empty for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException ("Staff name cannot be empty");
		}
		if(updatedStaff.getEmail()== null ||updatedStaff.getEmail().isBlank()) {
			logger.warn("Email cannot be empty for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException("Staff email cannot be empty");
		}
		if(updatedStaff.getNic()== null ) {
			logger.warn("NIC cannot be empty for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException("Staff nic cannot be null");
		}
		
		if(updatedStaff.getAddress()== null ||updatedStaff.getAddress().isBlank()) {
			logger.warn("Address cannot be empty for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException("Staff address cannot be empty");
		}
		
		if(updatedStaff.getOccupation()== null ||updatedStaff.getOccupation().isBlank()) {
			logger.warn("Occupation cannot be empty for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException("Staff occupation cannot be empty");
		}
		if(updatedStaff.getSalary()<0) {
			logger.warn("Invalid Salary for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException("Staff salary cannot be less than 0");
		}
		if (updatedStaff.getAge()<18 ) {
			logger.warn("Staff age cannot be less than 18 for staff {}",updatedStaff.getName());
			throw new InvalidStaffInputException("Staff Age cannot be less than 18");
		}
		
		 if(updatedStaff.getPhoneno().length()!=10) {
			 logger.warn("Invalid Phone number for staff {}",updatedStaff.getName());
		    	throw new InvalidInputException("Length of Staff phonenumber cannot be lesser than or greater than 10");
		    }
		    if (!updatedStaff.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
		    	logger.warn("Invalid Email format for staff {}",updatedStaff.getName());
		    	throw new InvalidInputException("Invalid Email format");
		    }
		
		existingStaff.setName(updatedStaff.getName());
		existingStaff.setAddress(updatedStaff.getAddress());
		existingStaff.setNic(updatedStaff.getNic());
		existingStaff.setSalary(updatedStaff.getSalary());
		existingStaff.setAge(updatedStaff.getAge());
		existingStaff.setOccupation(updatedStaff.getOccupation());
		existingStaff.setEmail(updatedStaff.getEmail());
		existingStaff.setPhoneno(updatedStaff.getPhoneno());
		staffRepository.save(existingStaff);
		
		return "Staff updated in Database successfully";
	}
	
	public List<Staff> getStaffByOccupation(String occupation) {
		List<Staff> staffList = staffRepository.findStaffByOccupation(occupation);
		if (staffList.isEmpty()) {
			throw new StaffNotFoundException("No Staff with occupation: "+occupation);
		}
		logger.info("Getting the staff by staff Id");
		return staffRepository.findStaffByOccupation(occupation);
	}
	
	public String deleteStaffByCode(Long code) {
		if(!staffRepository.existsById(code)) {
			throw new StaffNotFoundException("Staff with id "+code+" Not Found");
		}
		logger.info("Deleting the staff with id "+code);
		staffRepository.deleteById(code);
		return "Staff with id "+code+ " deleted successfully";
	}
}
