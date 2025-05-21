package com.payment.dto;

public class BillingDTO {
	
	private Long billId;
	private Long price;
	private String email;
	private Integer reservation_code;
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getBillId() {
		return billId;
	}
	public Long getPrice() {
		return price;
	}
	
	public void setBillId(Long billId) {
		this.billId=billId;
	}
	public void setPrice(Long price) {
		this.price=price;
	}

	public Integer getReservation_code(){
		return reservation_code;
	}
	public void setReservation_code(Integer reservation_code)
	{
		this.reservation_code=reservation_code;
	}
	public BillingDTO(Long billId,Long price,String email,Integer reservation_code) {
		this.billId = billId;
		this.price=price;
		this.email= email;
		this.reservation_code= reservation_code;
		
	}
	
	public BillingDTO() {
		
	}
	
	

}
