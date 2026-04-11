package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

	private final EmailService emailService;

	public OtpService(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	private Map<String, String> otpStorage = new HashMap<>();

	public void sendOtp(String email) {
		String otp = generateOtp();
		otpStorage.put(email, otp);
		emailService.sendOtp(email, otp);
	}
	
	 public boolean verifyOtp(String email, String otp) {
	        String storedOtp = otpStorage.get(email);

	        if (storedOtp != null && storedOtp.equals(otp)) {
	            otpStorage.remove(email); // remove after success
	            return true;
	        }
	        return false;
	    }
	 
	 
	 private String generateOtp() {
	        return String.valueOf((int)(Math.random() * 900000) + 100000);
	    }


}
