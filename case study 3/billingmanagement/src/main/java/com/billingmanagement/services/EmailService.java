
	
	package com.billingmanagement.services;

	import com.billingmanagement.entity.Billing;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.mail.SimpleMailMessage;
	import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.stereotype.Service;

	@Service
	public class EmailService {

	    @Autowired
	    private JavaMailSender mailSender;

	    public void sendBillingConfirmationEmail(Billing billing) {
	        SimpleMailMessage message = new SimpleMailMessage();

	        message.setTo(billing.getEmail());
	        message.setSubject("Billing Confirmation - OHMS");

	        String body = "Dear Guest,\n\n" +
	                "Thank you for staying at OHMS!\n\n" +
	                "Here are your billing details:\n" +
	                "Reservation ID: " + billing.getReservation_code() + "\n" +
	                "Food Quantity: " + billing.getFoodQuantity() + "\n" +
	               "Price: "+billing.getPrice()+"\n"+
	                "If you have any questions, feel free to contact us.\n\n" +
	                "Best regards,\n" +
	                "OHMS Billing Team";

	        message.setText(body);
	        mailSender.send(message);


}
	}
