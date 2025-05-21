package com.usermanagement.exception;

public class DuplicateGuestException extends RuntimeException {
	public DuplicateGuestException (String message) {
		super(message);
	}
}
