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

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	private final CartService cartSrvice;
	private final ProductService productService;
	private final UserService userservice;

	public CartController(CartService cartSrvice, ProductService productService, UserService userservice) {
		super();
		this.cartSrvice = cartSrvice;
		this.productService = productService;
		this.userservice = userservice;
	}

	@GetMapping("/cart")
	public String getShopCartPage(Authentication authentication, Model m) {

		String email = authentication.getName();

		Users user = userservice.getUserByEmail(email);

		/* Users user = (Users) session.getAttribute("user"); */

		if (user == null) {
			return "redirect:/userLoginPage";
		}
		List<Cart> cartList = cartSrvice.getUserCartDetails(user);
		double cart_Total = cartSrvice.getCartTotal(cartList);
		m.addAttribute("cart", cartList);
		m.addAttribute("user", user);  
		m.addAttribute("sub_total", cart_Total);
		return "shop-cart";

	}

	@PostMapping("/addToCart/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable int productId, Authentication authentication) {
		System.out.println("Cart Product ID: " + productId);
		String email = authentication.getName();
		Users u = userservice.getUserByEmail(email);
		Product product = productService.getFashionById(productId);
		System.out.println("product" + product);
		cartSrvice.addToCart(u, product);
		return new ResponseEntity<String>("Item Added to cart", HttpStatus.OK);

	}

	@GetMapping("/increaseQty")
	public String increaseQty(@RequestParam int cartId) {

		Cart cart = cartSrvice.findById(cartId);

		if (cart != null) {

			cartSrvice.increaseQty(cart);

		}

		return "redirect:/cart";
	}

	@GetMapping("/decreaseQty")
	public String decreaseQty(@RequestParam int cartId) {

		Cart cart = cartSrvice.findById(cartId);

		if (cart != null) {

			cartSrvice.decreaseQty(cart);

		}

		return "redirect:/cart";
	}

	@DeleteMapping("/removeFromCart/{cartId}")
	public ResponseEntity<String> removeFromCart(@PathVariable int cartId) {
		Cart cart = cartSrvice.findById(cartId);
		cartSrvice.deleteCartItem(cart);
		return new ResponseEntity<String>("Removed from cart", HttpStatus.OK);

	}

}
