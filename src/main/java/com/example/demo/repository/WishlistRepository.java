package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.model.Wishlist;
import java.util.List;


@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
	List<Wishlist> findByUser(Users user);
	List<Wishlist> findByUserAndProduct(Users user, Product product);
	
	@Query("SELECT w.product.product_id FROM Wishlist w WHERE w.user = :user")
	List<Integer> findProductIdsByUser(@Param("user") Users user);
	int  getWishlistCountByUser(Users u);
	int countByUser(Users user);
	void deleteByUserAndProduct(Users user, Product product);
	
	

}
