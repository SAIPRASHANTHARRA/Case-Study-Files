package com.billingmanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billingmanagement.dto.ReservationDTO;
import com.billingmanagement.entity.Billing;
import com.billingmanagement.feign.ReservationFeign;
import com.billingmanagement.services.BillingService;

@RestController
@RequestMapping("billing")
public class BillingController {

	@Autowired
	BillingService billingService;
	
	@Autowired
	 ReservationFeign reservationFeign;
	
	@GetMapping("/reservation/reservationdetails/{reservationId}")
	public ReservationDTO getReservationDetails(@PathVariable("reservationId") Integer reservationId) {
		return reservationFeign.getReservationDetails(reservationId);
	}
	
	@PostMapping ("/bill/{reservationId}/{foodQuantity}")
		public Billing generateBill (@PathVariable Integer reservationId,@PathVariable Integer foodQuantity){
		
		return billingService.generateBill(reservationId,foodQuantity);
		
		
	} 
	
	@GetMapping ("/bill/{billId}")
	public Billing getBillByBillId(@PathVariable Long billId) {
		return billingService.getBillByBillId(billId);
	}
	
	
	
}
