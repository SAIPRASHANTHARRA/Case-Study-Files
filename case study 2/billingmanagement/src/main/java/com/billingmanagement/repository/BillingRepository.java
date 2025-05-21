package com.billingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billingmanagement.entity.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing,Long> {
	

}
