package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.model.Users;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {

	private final WishlistService wishlistService;

	private final CartService cartService;
	private final UserService userservice;

	public GlobalController(WishlistService wishlistService, CartService cartService, UserService userservice) {
		super();
		this.wishlistService = wishlistService;
		this.cartService = cartService;
		this.userservice = userservice;
	}

	@ModelAttribute
	public void addCommonData(Model model, Authentication authentication) {
	    int wishlistCount = 0;
	    int cartCount = 0;
	    List<Integer> wishlistIds = new ArrayList<>();

	    if (authentication != null && authentication.isAuthenticated()) {
	        String email = authentication.getName();
	        Users u = userservice.getUserByEmail(email);
	        if (u != null) {
	            wishlistCount = wishlistService.getWishlistCount(u);
	            cartCount = cartService.getCartCount(u);
	            wishlistIds = wishlistService.getWishlistProductIds(u);
	        }
	    }

	    model.addAttribute("wishlistCount", wishlistCount);
	    model.addAttribute("cartCount", cartCount);
	    model.addAttribute("wishlistIds", wishlistIds);
	}

}
