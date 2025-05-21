package com.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionhandler {
	
	@ExceptionHandler(BillNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleBillNotFoundException(BillNotFoundException e){
		ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Bill Not Found",e.getMessage());
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
