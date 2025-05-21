package com.billingmanagement.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billingmanagement.dto.ReservationDTO;
import com.billingmanagement.entity.Billing;
import com.billingmanagement.exception.BillNotFoundException;
import com.billingmanagement.exception.GlobalExceptions;
import com.billingmanagement.exception.ReservationNotFoundException;
import com.billingmanagement.exception.RoomsNotFoundException;
import com.billingmanagement.feign.ReservationFeign;
import com.billingmanagement.repository.BillingRepository;


@Service
public class BillingService {

    private static final Logger logger = LoggerFactory.getLogger(BillingService.class);


	@Autowired
	BillingRepository billingRepository;
	
	@Autowired
	ReservationFeign reservationFeign;

	@Autowired
	EmailService emailService;
	

	
	public Billing generateBill(Integer reservationId,Integer foodQuantity) {
        logger.info("Generating bill for Reservation ID: {}, Food Quantity: {}", reservationId, foodQuantity);

		ReservationDTO  reservation = reservationFeign.getReservationDetails(reservationId);
		
		
		
		if(reservation==null ) {
            logger.warn("Reservation not found with Reservation ID: {}", reservationId);
			throw new ReservationNotFoundException("Reservation with ReservationId "+reservationId+ " Not Found" );
		} 
		
		if (reservationId== null) {
            logger.warn("Null reservation ID provided");

			throw new ReservationNotFoundException("Reservation with ReservationId"+ reservationId );
		}
		
		
		Integer capacity= reservation.getCapacity();
		String room_type= reservation.getRoom_type();
		Integer no_of_nights= reservation.getNo_of_nights();
		String email = reservation.getEmail();
		Integer no_of_nights_extended = reservation.getNo_of_nights_extended();
		Integer pricePerNight;
		Integer pricePerExtendedNight;
		if(room_type == null) {
            logger.warn("Room type is null for Reservation ID: {}", reservationId);

			throw new RoomsNotFoundException("Rooms Not Found with room type: "+room_type);
		}
		
		if (room_type.equals("Deluxe")) {
			pricePerNight=1000;
			pricePerExtendedNight = 700;
		}
		
		else if (room_type.equals("Standard")) {
			pricePerNight=500;
			pricePerExtendedNight= 200;
		}
		else if (room_type.equals("Basic")) {
			pricePerNight=300;
			pricePerExtendedNight=100;
		}
		else {
			throw new IllegalArgumentException("Room type Not Found");
		}
		Integer foodCharge = 200*foodQuantity;

		Integer totalPrice = pricePerNight*capacity*no_of_nights+foodCharge +no_of_nights_extended*pricePerExtendedNight	;
        logger.info("Calculated Total Price: {} for Reservation ID: {}", totalPrice, reservationId);
		
		
		Billing billing = new Billing (reservationId,totalPrice,foodQuantity,email);
		Billing savedBilling= billingRepository.save(billing);
		
		emailService.sendBillingConfirmationEmail(savedBilling);

		
		return savedBilling;
	}
	
	public Billing getBillByBillId(Long billingId) {
		logger.info("Fetching bill details for Bill ID: {}", billingId);
		return billingRepository.findById(billingId).orElseThrow(()->  {         
		logger.warn("Bill not found with Bill ID: {}", billingId);
 return  new BillNotFoundException("Bill Not found with BillId: "+billingId);
 });
	}
	
	
}
