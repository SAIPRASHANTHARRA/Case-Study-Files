package com.usermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.entity.Guest;
import com.usermanagement.service.GuestService;

@RestController
@RequestMapping("guest")
public class GuestController {


	@Autowired
	GuestService guestService;

	@GetMapping("allGuests")
	public List<Guest> getAllGuests() {
		return guestService.getAllGuests();
	}

	@GetMapping("/guestId/{guestId}")
	public Guest getGuestById(@PathVariable Long guestId) {
		return guestService.getGuestById(guestId);
	}

	
	@PutMapping("/update/{id}")
	public String updateGuest(@PathVariable Long id, @RequestBody Guest updatedGuest) {
	    String guest = guestService.updateGuest(id, updatedGuest);
	    return guest;
	}


	
	@PostMapping("addGuest")
	public String addGuest(@RequestBody Guest guest) {
		return guestService.addGuest(guest);
	}
	
	

}