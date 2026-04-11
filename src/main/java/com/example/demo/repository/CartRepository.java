package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.Users;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	List<Cart> findByUser(Users user);
	Cart findByUserAndProduct(Users user, Product product);
	void deleteByUser(Users user);
	int countByUser(Users u);

}
