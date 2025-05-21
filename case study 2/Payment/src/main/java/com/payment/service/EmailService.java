package com.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.payment.entity.Payment;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPaymentLinkEmail(Payment payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(payment.getEmail());
        message.setSubject("Your Payment Link - Online Hotel Management System");
        message.setText("Dear Guest,\n\n"
                + "Please use the following link to complete your payment:\n\n"
                + payment.getPaymentLink() + "\n\n"
                + "Bill ID: " + payment.getBillId() + "\n"
                + "Amount: ₹" + payment.getAmount() / 100 + "\n\n"
                + "Thank you for choosing our hotel!\n\n"
                + "Regards,\nOnline Hotel Management Team");
        mailSender.send(message);
    }

    public void sendPaymentSuccessEmail(Payment payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(payment.getEmail());
        message.setSubject("Payment Successful - Online Hotel Management System");
        message.setText("Dear Guest,\n\n"
                + "We have received your payment successfully.\n\n"
                + "Bill ID: " + payment.getBillId() + "\n"
                + "Amount Paid: ₹" + payment.getAmount() / 100 + "\n"
                + "Payment Status: " + payment.getStatus() + "\n\n"
                + "Thank you for staying with us!\n\n"
                + "Regards,\nOnline Hotel Management Team");
        mailSender.send(message);
    }
}
