package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.model.Wishlist;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WishlistController {

	private final WishlistService wishlistService;
	private final ProductService productService;
	private final CartService cartService;
	private final UserService userservice;

	public WishlistController(WishlistService wishlistService, ProductService productService, CartService cartService,
			UserService userservice) {
		super();
		this.wishlistService = wishlistService;
		this.productService = productService;
		this.cartService = cartService;
		this.userservice = userservice;
	}

	@PostMapping("/addToWishlist/{productId}")
	public ResponseEntity<String> addToWishlist(@PathVariable int productId, Authentication authentication) {

		String email = authentication.getName();

		Users u = userservice.getUserByEmail(email);
		Product product = productService.getFashionById(productId);

		boolean wishlist = wishlistService.getWishlisted(u, product);
		if (wishlist) {
			return new ResponseEntity<String>("added", HttpStatus.OK);
		}
		return new ResponseEntity<String>("removed", HttpStatus.OK);

	}

	@GetMapping("/wishlistpage")
	public String getWishlistPage(Model m, Authentication authentication) {
		
		  if (authentication != null && authentication.isAuthenticated()) {
		
		String email = authentication.getName();
		Users u = userservice.getUserByEmail(email);
		List<Wishlist> wishlist = wishlistService.getWishlistByUserId(u);
		/* List<Product> productList = productService.getProductDetails(wishlist); */
		m.addAttribute("wishlist", wishlist);
		  }
		return "wishlist";

	}

	@PostMapping("/addToCartFromWishlist/{wishlistId}")
	public ResponseEntity<String> addToCart(@PathVariable int wishlistId, Authentication authentication) {
		String email = authentication.getName();
		Users u = userservice.getUserByEmail(email);
		Wishlist item = wishlistService.getById(wishlistId);
		cartService.addToCart(u, item.getProduct());
		wishlistService.deleteWishlist(wishlistId);
		return new ResponseEntity<String>("Added to cart", HttpStatus.OK);

	}

	@DeleteMapping("/removeWishlist/{id}")
	public ResponseEntity<String> removeWishlist(@PathVariable int id) {
		wishlistService.deleteWishlist(id);
		return new ResponseEntity<String>("Wishlist deleted successfully", HttpStatus.OK);
	}

}
