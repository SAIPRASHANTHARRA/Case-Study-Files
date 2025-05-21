package com.usermanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

boolean existsByEmail(String email);

boolean existsByPhonenumber(String phonenumber);

public Guest findGuestByEmail(String email);




	

}