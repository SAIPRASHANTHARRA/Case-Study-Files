package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.BillingDTO;
import com.payment.entity.Payment;
import com.payment.feign.BillingFeign;
import com.payment.service.PaymentService;
import com.razorpay.RazorpayException;

//@CrossOrigin(origins = {"http://127.0.0.1:5500","http://127.0.0.1:8080"}, allowCredentials = "true")

@RestController
@RequestMapping("payment")

public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	BillingFeign billingFeign;
	

	
/*	@PostMapping("/payment/createLink")
	public String createPaymentLink(@RequestParam ) */
	
	@GetMapping ("billing/bill/{billId}")
	public BillingDTO getBillByBillId(@PathVariable Long billId) {
		return billingFeign.getBillByBillId(billId);
	}

	@PostMapping("/create-payment/{billId}")
	public String createPayment(@PathVariable Long billId) throws RazorpayException {
		return paymentService.createPayment(billId);
		
	} 
	
	
/*	@PostMapping("/{billId}")
	public String createPaymentLink(@PathVariable Long billId) {
		return paymentService.createPaymentLink(billId);
	}   */
	@GetMapping("/{order_receipt_id}")
	public Payment getPaymentByPaymentId(@PathVariable Integer order_receipt_id) {
		return paymentService.getPaymentByPaymentId(order_receipt_id);
	}
	
/*	@GetMapping("/{billId}/link")
    public ResponseEntity<String> generatePaymentLink(@PathVariable Long billId) {
        String paymentLink = paymentService.createPaymentLink(billId);

        if (paymentLink == null || paymentLink.isEmpty()) {
            return ResponseEntity.notFound().build(); // Returns 404 if no link is found
        }

        return ResponseEntity.ok(paymentLink); // Returns 200 OK with the link
    } */
}
