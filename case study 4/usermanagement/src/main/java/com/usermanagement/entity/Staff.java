package com.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Staff {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="staff_code")
	private Long code;
	
	@Column(name="staff_name")
	private String name;
	
	@Column(name="staff_address")
	private String address;
	
	@Column(name="staff_nicId")
	private Long nic;
	
	@Column(name="staff_salary")
	private Integer salary;

	@Column(name="staff_age")
	private Integer age;
	
	@Column(name="staff_occupation")
	private String occupation;
	
	@Column(name="staff_email")
	private String email;
	
	@Column(name="staff_phoneno")
	private String phoneno;

	public String getPhoneno(){
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno=phoneno;
	}
	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address =address;
	}

	public Long getNic() {
		return nic;
	}

	public void setNic(Long nic) {
		this.nic = nic;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Staff(String name, String address, Long nic, Integer salary, Integer age, String occupation,
		String phoneno,	String email) {

		this.name = name;
		this.address = address;
		this.nic = nic;
		this.salary = salary;
		this.age = age;
		this.occupation = occupation;
		this.email = email;
		this.phoneno=phoneno;
	}

	public Staff() {

	}
	
	

}
