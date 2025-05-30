package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
	
	public Payment getPaymentByPaymentId(Integer order_receipt_id);
	
	

}
