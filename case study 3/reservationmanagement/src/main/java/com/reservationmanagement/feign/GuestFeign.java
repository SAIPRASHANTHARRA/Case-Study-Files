package com.reservationmanagement.feign;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.reservationmanagement.dto.GuestDTO;

@FeignClient(name="USERMANAGEMENT",url="http://localhost:8001")
public interface GuestFeign {
	

	@GetMapping("/guest/guestId/{guestId}")
	public GuestDTO getGuestById(@PathVariable("guestId") Long guestId);
	
	  @GetMapping("/guest/AllGuests")
	    List<GuestDTO> getAllGuests();

}
