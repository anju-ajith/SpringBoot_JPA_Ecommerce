package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepo;

	public CartServiceImpl(CartRepository cartRepo) {
		this.cartRepo = cartRepo;
	}

	@Override
	public void addToCart(Users u, Product p) {
		// TODO Auto-generated method stub
		Cart existingCart = cartRepo.findByUserAndProduct(u, p);

		if (existingCart != null) {

			int product_count = existingCart.getQuantity() + 1; // update cart_qty

			existingCart.setQuantity(product_count);
			double cart_total = p.getPrice() * product_count; // update cart_total

			existingCart.setCart_total(cart_total);
			cartRepo.save(existingCart);

		} else {
			Cart cart = new Cart();
			cart.setUser(u);
			cart.setProduct(p);
			cart.setQuantity(1);
			cart.setCart_total(p.getPrice());
			cartRepo.save(cart);
		}
	}

	@Override
	public List<Cart> getUserCartDetails(Users user) {
		// TODO Auto-generated method stub
		List<Cart> cartDetails = cartRepo.findByUser(user);
		return cartDetails;
	}

	@Override
	public Cart findById(int cartId) {
		// TODO Auto-generated method stub
		Cart cart = cartRepo.findById(cartId).orElse(null);
		return cart;
	}

	@Override
	public void increaseQty(Cart cart) {
		// TODO Auto-generated method stub
		int qty= cart.getQuantity() + 1;
		cart.setQuantity(qty);
		cart.updateTotal();
		cartRepo.save(cart);
	}

	@Override
	public void decreaseQty(Cart cart) {
		
		int qty= cart.getQuantity() - 1;
		 if (cart.getQuantity() > 1) {
			 
	            cart.setQuantity(qty);
	            cart.updateTotal();
	            cartRepo.save(cart);
	        } else {
	            cartRepo.delete(cart); // remove if qty = 1
	        }
		
	}

	@Override
	public double getCartTotal(List<Cart> cartList) {
		// TODO Auto-generated method stub
		double subtotal=0;
		for(Cart c :cartList ) {
			subtotal+=c.getCart_total();
		}
		return subtotal;
	}

	@Override
	public void deleteCartItem(Cart cart) {
		// TODO Auto-generated method stub
		  cartRepo.delete(cart);
	}

	@Override
	public int getCartCount(Users u) {
		// TODO Auto-generated method stub
		return cartRepo.countByUser(u);
	}

}
