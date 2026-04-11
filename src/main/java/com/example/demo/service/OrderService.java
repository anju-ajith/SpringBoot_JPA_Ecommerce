package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Orders;
import com.example.demo.model.Users;

public interface OrderService {
	void placeOrder(Users user);

	List<Orders> getorderDetails(Users user);

	List<Orders> getAllOrderDetails();

	Orders getOrderById(int orderId);

	void updateOrderStatus(Orders order);

	long getTotalOrders();

	double calculateTotalRevenue();
	List<Double> getMonthlyRevenue();
	
}
