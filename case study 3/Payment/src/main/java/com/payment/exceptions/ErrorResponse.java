package com.payment.exceptions;

public class ErrorResponse {
	private Integer status;
	private String error;
	private String message;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorResponse(Integer status, String error, String message) {
		this.status = status;
		this.error = error;
		this.message = message;
	}
	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}
	
	

}
