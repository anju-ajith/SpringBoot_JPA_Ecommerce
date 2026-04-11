package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.Users;

public interface CartService {
	
	void addToCart(Users u , Product p);
	List<Cart> getUserCartDetails(Users user);
	Cart findById(int cartId);
	void increaseQty(Cart cart);
	void decreaseQty(Cart cart);
	double getCartTotal(List<Cart> cartList);
	void deleteCartItem(Cart cart);
	int getCartCount(Users u);

}
