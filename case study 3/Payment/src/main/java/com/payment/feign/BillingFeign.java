package com.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.payment.dto.BillingDTO;



@FeignClient(name="BILLINGMANAGEMENT",url="http://localhost:8007/")
public interface BillingFeign {
	
	@GetMapping ("billing/bill/{billId}")
	public BillingDTO getBillByBillId(@PathVariable("billId") Long billId);

}
