package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.model.Wishlist;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController {

	private final ProductService productService;
	

	public HomePageController(ProductService productService) {
		super();
		this.productService = productService;
		
	}

	@GetMapping("/")
	public String getHomePage() {
		return "index";
	}

	@GetMapping("/contacts")
	public String getContactPage() {
		return "contact";

	}

	@GetMapping("/shopNowNewborn")
	public String getNewbornShopPage(Model m
			 /* @RequestParam(defaultValue = "0") int page */) {

		List<Product> newbornlist = productService.getFashionByCatagory("newborn");

		/*
		 * int pageSize = 6; // products per page
		 * 
		 * Page<Product> productPage = productService.NewbornProducts(page, pageSize);
		 * 
		 * m.addAttribute("products", productPage.getContent());
		 * m.addAttribute("currentPage", page); m.addAttribute("totalPages",
		 * productPage.getTotalPages());
		 */
		m.addAttribute("products", newbornlist);
		return "shopNowNewborn";

	}

	@GetMapping("/shopNowBoysFashion")
	public String getBoysfashionShopPage(Model m) {
		List<Product> boyslist = productService.getFashionByCatagory("boys");
		m.addAttribute("products", boyslist);
		return "shopNowBoysFashion";

	}

	@GetMapping("/shopNowGirlsFashion")
	public String getGirlsfashionShopPage(Model m) {
		List<Product> girlslist = productService.getFashionByCatagory("girls");

		m.addAttribute("products", girlslist);
		return "shopNowGirlsFashion";

	}

	@GetMapping("/checkout")
	public String getCheckoutPage() {
		return "checkout";

	}

	@GetMapping("/userLoginPage")
	public String getUserLoginPage() {
		/* m.addAttribute("login", new Users()); */
		return "userLogin";

	}

	@GetMapping("/userRegistration")
	public String getUserRegistrationPage(Model m) {
		m.addAttribute("user", new Users());
		return "userRegistration";

	}

	@GetMapping("/details")
	public String getprdDetailsPage() {
		return "product-details";

	}

	@GetMapping("/orderSuccess")
	public String getUseOrderSuccessPage() {
		return "payment";

	}

}
