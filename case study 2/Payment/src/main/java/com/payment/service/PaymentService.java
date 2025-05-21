package com.payment.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payment.dto.BillingDTO;
import com.payment.entity.Payment;
import com.payment.exceptions.BillNotFoundException;
import com.payment.feign.BillingFeign;
import com.payment.repository.PaymentRepository;
import com.payment.repository.RazorPayService;
import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
	@Autowired
	BillingFeign billingFeign;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	EmailService emailService;
	
	@Value("${razorpay.api.key}")
	private String apiKey;
	
	@Value("${razorpay.api.secretkey}")
	private String apiSecret;
	
	public String createPayment (Long billId) throws RazorpayException{
		
		BillingDTO billingDetails = billingFeign.getBillByBillId(billId);
		RazorpayClient razorpayClient= new RazorpayClient(apiKey,apiSecret);
		
		if (billingDetails==null) {
			throw new BillNotFoundException("Bill Not found with BillId: "+billId );
		}
		
		String email = billingDetails.getEmail();
		Long price= billingDetails.getPrice()*100;
		//Integer reservationId = billingDetails.getReservation_code();
		
		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("amount",price);
		paymentLinkRequest.put("currency", "INR");
//	paymentLinkRequest.put("receipt", "order_receipt_" + billId); // ✅ Correct format
		//paymentLinkRequest.put("description", "Payment for Bill ID " + billId);
		
		// paymentLinkRequest.put("description", "Payment for Bill ID " + billId);
		//    paymentLinkRequest.put("customer", new JSONObject().put("email", email));
		
		
	/*	 JSONObject customerDetails = new JSONObject();
			customerDetails.put("email", email);
			paymentLinkRequest.put("customer", customerDetails); */
			
	 
		
		
		
		PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
		String paymentLinkUrl = paymentLink.get("short_url");
	Payment payment = new Payment (billId,price,"INR",email,"PENDING",paymentLinkUrl);
	emailService.sendPaymentLinkEmail(payment);
		paymentRepository.save(payment);
		return paymentLinkUrl;
	}  
	
/*	public String createPaymentLink(Long billId) {
	    // Fetch billing details
	    BillingDTO billingDetails = billingFeign.getBillByBillId(billId);
	    if (billingDetails == null) {
	        throw new BillNotFoundException("Billing with ID " + billId + " not found");
	    }

	    // Initialize Razorpay Client
	    RazorpayClient razorpayClient;
	    try {
	        razorpayClient = new RazorpayClient(apiKey, apiSecret);
	    } catch (RazorpayException e) {
	        throw new RuntimeException("Failed to initialize Razorpay Client: " + e.getMessage(), e);
	    }

	    // Create Payment Link Request
	    JSONObject paymentLinkRequest = new JSONObject();
	    paymentLinkRequest.put("amount", billingDetails.getPrice() * 100); // Convert to paisa
	    paymentLinkRequest.put("currency", "INR");
	    paymentLinkRequest.put("description", "Payment for Bill ID " + billId);
	    paymentLinkRequest.put("receipt", "order_receipt_" + billId);

	    JSONObject customerDetails = new JSONObject();
	 //   customerDetails.put("name", billingDetails.getname());
	    customerDetails.put("email", billingDetails.getEmail());
	 //   customerDetails.put("contact", billingDetails.getPhone());
	    paymentLinkRequest.put("customer", customerDetails);

	    // Enable SMS and Email notifications
	    JSONObject notify = new JSONObject();
	    notify.put("sms", true);
	    notify.put("email", true);
	    paymentLinkRequest.put("notify", notify);

	    // Callback URL (Razorpay will redirect after payment)
	 //   paymentLinkRequest.put("callback_url", "https://yourwebsite.com/payment-success");
	  //  paymentLinkRequest.put("callback_method", "get");

	    // Create Payment Link
	    PaymentLink paymentLink;
	    try {
	        paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
	    } catch (RazorpayException e) {
	        throw new RuntimeException("Failed to create Razorpay payment link: " + e.getMessage(), e);
	    }

	    String paymentUrl = paymentLink.get("short_url");
	    System.out.println("Generated Payment Link: " + paymentUrl);

	    // Save payment details in DB
	    Payment payment = new Payment(
	        billId, 
	        paymentLink.get("id"), 
	        billingDetails.getPrice(), 
	        "INR", 
	        billingDetails.getEmail(), 
	        "PENDING", 
	        billingDetails.getReservation_code()
	    );
	    paymentRepository.save(payment);

	    return paymentUrl;
	} */


	public Payment getPaymentByPaymentId(Integer order_receipt_id) {
		return paymentRepository.getPaymentByPaymentId(order_receipt_id);
	}

	public String updatePaymentStatus(Long paymentId) {
	    logger.info("Updating payment status for Payment ID: {}", paymentId);

	    Payment payment = paymentRepository.findById(paymentId)
	            .orElseThrow(() -> {
	                logger.warn("Payment not found with ID: {}", paymentId);
	                return new BillNotFoundException("Payment not found with ID: " + paymentId);
	            });

	    if ("Paid".equalsIgnoreCase(payment.getStatus())) {
	        logger.info("Payment is already marked as Paid for Payment ID: {}", paymentId);
	        return "Payment is already marked as Paid.";
	    }

	    if (!"Pending".equalsIgnoreCase(payment.getStatus())) {
	        logger.warn("Invalid payment status update for Payment ID: {}. Current Status: {}", paymentId, payment.getStatus());
	        return "Cannot update payment status. Current status: " + payment.getStatus();
	    }

	    payment.setStatus("Paid");
	    paymentRepository.save(payment);
	    emailService.sendPaymentSuccessEmail(payment);

	    logger.info("Payment status successfully updated to 'Paid' for Payment ID: {}", paymentId);
	    return "Payment status updated successfully to Paid.";
	}


}
