
	package com.reservationmanagement.service;

	import com.reservationmanagement.entity.Reservation;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.mail.SimpleMailMessage;
	import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.stereotype.Service;

	@Service
	public class EmailService {

	    @Autowired
	    private JavaMailSender mailSender;

	    public void sendReservationConfirmationEmail(Reservation reservation) {
	        SimpleMailMessage message = new SimpleMailMessage();

	        message.setTo(reservation.getEmail());
	        message.setSubject("Reservation Confirmation - OHMS");

	        String body = "Dear Guest,\n\n" +
	                "Thank you for reserving a room at OHMS!\n\n" +
	                "Here are your reservation details:\n" +
	                "Reservation Code: " + reservation.getReservation_code() + "\n" +
	                "Guest ID: " + reservation.getGuestId() + "\n" +
	                "Room Number: " + reservation.getRoomno() + "\n" +
	                "Room Type: " + reservation.getRoom_type() + "\n" +
	                "Capacity: " + reservation.getCapacity() + "\n" +
	                "Check-in Date: " + reservation.getCheckinDate() + "\n" +
	                "Check-out Date: " + reservation.getCheckoutDate() + "\n" +
	                "Adults: " + reservation.getNo_of_adults() + "\n" +
	                "Children: " + reservation.getNo_of_children() + "\n\n" +
	                "We look forward to hosting you!\n\n" +
	                "Best regards,\n" +
	                "OHMS Team";

	        message.setText(body);
	        mailSender.send(message);
	    }
	}


