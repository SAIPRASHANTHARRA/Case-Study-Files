package com.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(GuestNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleGuestNotFoundException(GuestNotFoundException e){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Guest Not Found",e.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StaffNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleStaffNotFoundException(StaffNotFoundException e2){
		ErrorResponse errorResponse2 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Staff Not Found",e2.getMessage());
		return new ResponseEntity<>(errorResponse2,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateGuestException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateGuestException(DuplicateGuestException e3){
		ErrorResponse errorResponse3 = new ErrorResponse(HttpStatus.CONFLICT.value(),"Duplicate Entry",e3.getMessage());
		return new ResponseEntity<>(errorResponse3,HttpStatus.CONFLICT);
	}
	@ExceptionHandler(DuplicateStaffException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateStaffException(DuplicateStaffException e5){
		ErrorResponse errorResponse5 = new ErrorResponse(HttpStatus.CONFLICT.value(),"Duplicate Entry",e5.getMessage());
		return new ResponseEntity<>(errorResponse5,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e4){
		ErrorResponse errorResponse4 = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Values",e4.getMessage());
		return new ResponseEntity<>(errorResponse4,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidStaffInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidStaffInputException(InvalidStaffInputException e6){
		ErrorResponse errorResponse6 = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Values",e6.getMessage());
		return new ResponseEntity<>(errorResponse6,HttpStatus.BAD_REQUEST);
	}
}
