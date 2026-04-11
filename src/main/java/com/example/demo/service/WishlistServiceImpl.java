package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.model.Wishlist;
import com.example.demo.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistServiceImpl implements WishlistService {
	private final WishlistRepository wishlistRepo;

	public WishlistServiceImpl(WishlistRepository wishlistRepo) {
		this.wishlistRepo = wishlistRepo;
	}

	@Override
	@Transactional
	public boolean getWishlisted(Users user, Product product) {
		
		List<Wishlist> list = wishlistRepo.findByUserAndProduct(user, product);

		if (list.isEmpty()) {
			Wishlist w = new Wishlist();
			w.setUser(user);
			w.setProduct(product);
			wishlistRepo.save(w);
			return true;
		}
		wishlistRepo.deleteByUserAndProduct(user,product);
		return false;
		
	}

	@Override
	public List<Wishlist> getWishlistByUserId(Users u) {
		// TODO Auto-generated method stub
		List<Wishlist> wishlist = wishlistRepo.findByUser(u);
		return wishlist;
	}

	@Override
	public void deleteWishlist(int wishlistId) {
		// TODO Auto-generated method stub
		wishlistRepo.deleteById(wishlistId);
	}

	@Override
	public Wishlist getById(int wishlistId) {
		// TODO Auto-generated method stub
		Wishlist wishlist = wishlistRepo.getById(wishlistId);
		return wishlist;
	}

	@Override
	public List<Integer> getWishlistProductIds(Users u) {
		// TODO Auto-generated method stub
		return wishlistRepo.findProductIdsByUser(u);
	}

	@Override
	public int getWishlistCount(Users u) {
		// TODO Auto-generated method stub
		return wishlistRepo.countByUser(u);
	}

	

}
