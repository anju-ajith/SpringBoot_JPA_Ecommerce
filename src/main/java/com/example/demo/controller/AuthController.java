package com.example.demo.controller;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Users;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	private final OtpService otpService;
	private final UserService userService;

	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam String email, Model model) {
		otpService.sendOtp(email);
		model.addAttribute("email", email);
		return "verifyOtpPage";
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam String email, @RequestParam String otp,
			HttpServletRequest request/* HttpSession session */) {

		if (otpService.verifyOtp(email, otp)) {

			Users user = userService.getUserByEmail(email);
			/* session.setAttribute("user", user); */

			// Create Authentication object
			Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
					Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

			// Set authentication in Spring Security
			SecurityContextHolder.getContext().setAuthentication(auth);
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			return "redirect:/";
		} else {
			return "verifyOtpPage"; // show error
		}
	}

	public AuthController(OtpService otpService, UserService userService) {
		super();
		this.otpService = otpService;
		this.userService = userService;
	}

}
