package com.usermanagement.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

boolean existsByEmail(String email);

boolean existsByPhonenumber(String phonenumber);





	

}