package com.billingmanagement.dto;



public class ReservationDTO {
	
	private Integer no_of_nights;
	private String room_type;
	private Integer capacity;
	private String email;
	private Integer no_of_nights_extended;
//	@JsonProperty("reservation_code")
	private Integer guestId;
	

	public Integer getNo_of_nights_extended() {
		return no_of_nights_extended;
	}
	public void setNo_of_nights_extended(Integer no_of_nights_extended) {
		this.no_of_nights_extended=no_of_nights_extended;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}

	public Integer getNo_of_nights() {
		return no_of_nights;
	}

	public void setNo_of_nights(Integer no_of_nights) {
		this.no_of_nights = no_of_nights;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public Integer getGuestId() {
		return guestId;
	}

	public void setReservationId(Integer guestId) {
		this.guestId = guestId;
	}
	
	public Integer getCapacity() {
		return capacity;
	}
	
	public void setCapacity(Integer capacity) {
		this.capacity=capacity;
	}

	public ReservationDTO(Integer no_of_nights, String room_type, Integer guestId,Integer capacity,String email,Integer no_of_nights_extended) {
		this.no_of_nights = no_of_nights;
		this.room_type = room_type;
		this.guestId = guestId;
		this.capacity=capacity;
		this.email=email;
		//this.totalBill=calculateBill();
		this.no_of_nights_extended=no_of_nights_extended;
	}
	
	public ReservationDTO() {
		
	}
	

}
