package com.billingmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Billing {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long billId;
//	private String room_type;
	private Integer price;
	private Integer reservation_code;
	private Integer foodQuantity;
	private String email;
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getReservation_code() {
		return reservation_code;
	}
	public void setReservation_code(Integer reservation_code) {
		this.reservation_code = reservation_code;
	}
	public Integer getFoodQuantity() {
		return foodQuantity;
	}
	public void setFoodQuantity(Integer foodQuantity) {
		this.foodQuantity = foodQuantity;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	
	public Billing(Integer reservation_code, Integer price,Integer foodQuantity,String email) {
		this.foodQuantity=foodQuantity;
		this.reservation_code= reservation_code;
		this.price= price;
		this.email= email;
	}
	
	public Billing() {
		
	}
	
	
	
	

}
