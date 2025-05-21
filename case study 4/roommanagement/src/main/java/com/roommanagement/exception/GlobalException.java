package com.roommanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(RoomsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRoomsNotFoundException(RoomsNotFoundException e){
		ErrorResponse err=new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Rooms Not Found",e.getMessage());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e2){
		ErrorResponse err2= new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Input",e2.getMessage());
		return new ResponseEntity<>(err2,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateRoomNoException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateRoomNoException(DuplicateRoomNoException e3){
		ErrorResponse err3 = new ErrorResponse(HttpStatus.CONFLICT.value(),"Duplicate Room No",e3.getMessage());
		return new ResponseEntity<>(err3,HttpStatus.CONFLICT);
	}

}
