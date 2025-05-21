package com.usermanagement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name="guest")
public class Guest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long guestId;
	
	@Column(name="guest_phone_number")

	private String phonenumber;
	
	@Column(name="guest_company")
	private String company;
	
	@Column(name="guest_name")

	private String name;
	
	@Column(name="guest_email")
	private String email;

	@Column(name="gender")
	private String gender;
	
	@Column(name="guest_address")
	private String address;
	
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getGuestId() {
		return guestId;
	}
	
	public void setGuestId(Long guestId) {
		this.guestId=guestId;
	}
	public Guest(Long guestId,String phonenumber, String company, String name, String email, String gender, String address) {
		this.guestId=guestId;
		this.phonenumber = phonenumber;
		this.company = company;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.address = address;
	}
	public Guest() {
	}
	
	
	
}