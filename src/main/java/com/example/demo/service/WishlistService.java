package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.model.Wishlist;

public interface WishlistService {
	boolean getWishlisted(Users u, Product p);

	List<Wishlist> getWishlistByUserId(Users u);

	void deleteWishlist(int wishlistId);

	Wishlist getById(int wishlistId);

	List<Integer> getWishlistProductIds(Users u);
	int getWishlistCount(Users u);

}
