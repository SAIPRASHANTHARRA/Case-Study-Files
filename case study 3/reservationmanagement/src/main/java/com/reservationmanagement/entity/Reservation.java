package com.reservationmanagement.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer reservation_code;

	private Integer no_of_children;
	private Integer no_of_adults;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private Integer no_of_nights;
	@Column(name="booking_status")
	private String bookingStatus;
	private Long guestId;
	private String email;
	private Integer capacity;
	private String room_type;
//	private Integer extension_checkin;
//	private Integer extension_checkout;
	private Integer roomno;
	
	private Integer no_of_nights_extended;
	
	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public Integer getCapacity() {
		return capacity;
	}
	
	public void setCapacity(Integer capacity) {
		this.capacity=capacity;
	}
	
	
	public Integer getRoomno() {
		return roomno;
	}
	
	

	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setRoomno(Integer roomno) {
        this.roomno = roomno;
        if (roomno != null) {
            this.bookingStatus = "Booked";
        }
    }
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}




	public Long getGuestId() {
		return guestId;
	}
	
	public void setGuestId(Long guestId) {
		this.guestId=guestId;
	}

	public Integer getReservation_code() {
		return reservation_code;
	}

	public void setReservation_code(Integer reservation_code) {
		this.reservation_code = reservation_code;
	}

	public Integer getNo_of_children() {
		return no_of_children;
	}

	public void setNo_of_children(Integer no_of_children) {
		this.no_of_children = no_of_children;
	}

	public Integer getNo_of_adults() {
		return no_of_adults;
	}

	public void setNo_of_adults(Integer no_of_adults) {
		this.no_of_adults = no_of_adults;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
		setNo_of_nights();
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
		setNo_of_nights();
	}

	public Integer getNo_of_nights() {
		return no_of_nights;
	}


	public void setNo_of_nights() {
		if(checkinDate != null && checkoutDate != null) {
			this.no_of_nights=Math.toIntExact(ChronoUnit.DAYS.between(checkinDate, checkoutDate));
		}
		else {
			this.no_of_nights=0;
		}
		
	}
	
	public void setNo_of_nights_extended(Integer no_of_nights_extended) {
			this.no_of_nights_extended = no_of_nights_extended;
	} 
	/*
	public Integer getExtension_checkin(){
		return extension_checkin;
	}
	
	public void setExtension_checkin(Integer extension_checkin) {
		this.extension_checkin=extension_checkin;
	}
	
	public Integer getExtension_checkout() {
		return extension_checkout;
	}
	public void setExtension_checkout(Integer extension_checkout) {
		this.extension_checkout= extension_checkout;
	}
	*/
	public Integer getNo_of_nights_extended() {
		return no_of_nights_extended;
	}
	
	 

	public Reservation(Integer reservation_code, Integer no_of_children, Integer no_of_adults, LocalDate checkinDate,
Integer capacity,		String room_type,LocalDate checkoutDate, Long guestId,String bookingStatus,String email,Integer no_of_nights,Integer roomno
,Integer extension_checkin, Integer extension_checkout) {

		this.reservation_code = reservation_code;
		this.no_of_children = no_of_children;
		this.no_of_adults = no_of_adults; 
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.guestId=guestId;
		this.bookingStatus=bookingStatus;
		this.email=email;
		this.no_of_nights=no_of_nights;
		this.roomno=roomno;
		this.room_type=room_type;
		this.capacity=capacity;
		this.no_of_nights= no_of_nights;
	//	this.extension_checkin=extension_checkin;
	//	this.extension_checkout= extension_checkout;
	
	}

	public Reservation() {
	
		// TODO Auto-generated constructor stub
	}
	
	

}
