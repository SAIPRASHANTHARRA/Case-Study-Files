package com.roommanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="room_id")
	private Long roomID;
	
	@Column(name="room_number")
	private Integer roomno;
	
	@Column(name="room_type")
	private String type;
	
	@Column(name="room_capacity")
	private Integer capacity;
	
	@Column(name="room_status")
	private String status;
	
	

	
	
	
	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}



	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status= status;
	}

	public Integer getRoomno() {
		return roomno;
	}

	public void setRoomno(Integer roomno) {
		this.roomno = roomno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	

	public Room(Long roomID, Integer roomno, String type, Integer capacity, String status) {
		this.roomID = roomID;
		this.roomno = roomno;
		this.type = type;
		this.capacity = capacity;
		this.status = status;
		
	}

	public Room() {
		
	}
	
	
	
	

}
