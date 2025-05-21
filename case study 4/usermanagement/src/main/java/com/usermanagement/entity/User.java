package com.usermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	
	/*@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String usermail; 
	private String password;
	
	private String role;
	
	@OneToOne
	private Guest guest;
	
	@OneToOne
	private Staff staff;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public User(Long id, String usermail, String password, String role, Guest guest, Staff staff) {
		this.id = id;
		this.usermail = usermail;
		this.password = password;
		this.role = role;
		this.guest = guest;
		this.staff = staff;
	}
	
	public User() {
		
	} */
	@Id
	private int id;
	private String email;
	private String password;
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role=role;
	}
	
	@OneToOne
	@JoinColumn(name="guest_id")
	private Guest guest;
	
	@OneToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest=guest;
	}
	
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff= staff;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}
	
	
	
	

}
