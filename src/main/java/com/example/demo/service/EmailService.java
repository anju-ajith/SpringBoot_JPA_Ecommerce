package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	 private final JavaMailSender mailSender;

	 public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	 }
	    public void sendOtp(String toEmail, String otp) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Your OTP Login Code");
	        message.setText("Your OTP is: " + otp);

	        mailSender.send(message);
	      
	    }
	  
}
