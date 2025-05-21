package com.billingmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice

public class GlobalExceptions {
	
	
	
	@ExceptionHandler(ReservationNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException e1){
		ErrorResponse err2 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Reservation Id Not Found",e1.getMessage());
		return new ResponseEntity<>(err2,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BillNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleBillNotFoundException(BillNotFoundException e2){
		ErrorResponse err3 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Bill Not Found",e2.getMessage());
		return new ResponseEntity<>(err3,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RoomsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRoomsNotFoundException(RoomsNotFoundException e2){
		ErrorResponse err3 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Room Not Found",e2.getMessage());
		return new ResponseEntity<>(err3,HttpStatus.NOT_FOUND);
	}

}
