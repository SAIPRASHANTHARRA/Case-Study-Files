package com.reservationmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(GuestNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleGuestNotFoundException (GuestNotFoundException e1){
		ErrorResponse err1 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Guest not Found",e1.getMessage());
			return new ResponseEntity<>(err1,HttpStatus.NOT_FOUND);
	}
			
	@ExceptionHandler(ReservationNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException e2){
		ErrorResponse err2 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Reservation Not Found",e2.getMessage());
		return new ResponseEntity<>(err2,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RoomsNotAvailableException.class)
	public ResponseEntity<ErrorResponse> handleRoomsNotAvailableException(RoomsNotAvailableException e3){
		ErrorResponse err3 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Rooms not Available",e3.getMessage());
		return new ResponseEntity<>(err3,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidDatesException.class)
	public ResponseEntity<ErrorResponse> handleInvalidDatesException(InvalidDatesException e4){
		ErrorResponse err4 = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Dates Entered",e4.getMessage());
		return new ResponseEntity<>(err4,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoomDetailsException.class)
	public ResponseEntity<ErrorResponse> handleRoomDetailsException(RoomDetailsException e5){
		ErrorResponse err5 = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Room Details Error",e5.getMessage());
		return new ResponseEntity<>(err5,HttpStatus.NOT_FOUND);
	}

}
