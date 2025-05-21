package com.reservationmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuestDTO {
	
	@JsonProperty("guestId")
	private Long guestId;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("name")
	private String name;
	
	public Long getGuestId() {
		return guestId;
	}
	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public GuestDTO() {
		
	}
	public GuestDTO(Long guestId, String email, String name) {
		this.guestId = guestId;
		this.email = email;
		this.name = name;
	}
	

}
