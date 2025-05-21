package com.reservationmanagement.dto;

import java.time.LocalDate;

public class RoomDTO {
	
	private String status;
	private Integer capacity;
	private String type;
	private Integer roomno;
	
	
	
	public Integer getRoomno() {
		return roomno;
	}
	
	public void setRoomno(Integer roomno) {
		this.roomno=roomno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public RoomDTO() {
		
	}
	
	public RoomDTO(String status,Integer capacity,String type,Integer roomno) {
		this.status=status;
		this.capacity=capacity;
		this.type=type;
		this.roomno=roomno;
	}
	
	/*private LocalDate checkin_date;
	private LocalDate checkout_date;
	
	public LocalDate getCheckin_date() {
		return checkin_date;
	}
	public void setCheckin_date(LocalDate checkin_date) {
		this.checkin_date=checkin_date;
	}
	
	public LocalDate getCheckout_date() {
		return checkout_date;
	}
	public void setCheckout_date(LocalDate checkout_date) {
		this.checkout_date=checkout_date;
	} */

}
