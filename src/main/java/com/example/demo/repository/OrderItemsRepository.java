package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderItems;
import com.example.demo.model.Orders;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
	List<OrderItems> findByOrderIn(List<Orders> orders);
	
	List<OrderItems> findByOrder(Orders order);

}
