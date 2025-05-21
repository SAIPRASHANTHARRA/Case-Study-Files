package com.billingmanagement.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.billingmanagement.dto.ReservationDTO;

@FeignClient(name="RESERVATIONMANAGEMENT",url="http://localhost:8006/")
public interface ReservationFeign {
	
	
	@GetMapping("reservation/reservationdetails/{reservationId}")
	public ReservationDTO getReservationDetails(@PathVariable("reservationId") Integer reservationId);
	

}
