package com.billingmanagement.exception;

public class ReservationNotFoundException extends RuntimeException{

	public ReservationNotFoundException(String message) {
		super(message);
	}
}
