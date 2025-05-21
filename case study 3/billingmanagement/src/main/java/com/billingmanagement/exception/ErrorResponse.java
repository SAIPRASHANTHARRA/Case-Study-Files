package com.billingmanagement.exception;

public class ErrorResponse {
	
	private Integer status;
	private String message;
	private String error;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ErrorResponse(Integer status, String message, String error) {
		this.status = status;
		this.message = message;
		this.error = error;
	}
	
	public ErrorResponse() {
		
	}
	
	

}
