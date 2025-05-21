package com.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {

	public List<Staff> findStaffByOccupation(String occupation);
	
	boolean existsByEmail(String email);

	boolean existsByPhoneno(String phoneno);
	boolean existsByNic(Long nic);
	
	public Staff findStaffByEmail(String email);
	
}
