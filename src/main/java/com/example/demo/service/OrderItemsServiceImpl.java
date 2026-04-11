package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.OrderItems;
import com.example.demo.model.Orders;
import com.example.demo.model.Users;
import com.example.demo.repository.OrderItemsRepository;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderItemsServiceImpl implements OrderItemService {
	private final OrderItemsRepository orderItemRepo;

	public OrderItemsServiceImpl(OrderItemsRepository orderItemRepo) {
		super();
		this.orderItemRepo = orderItemRepo;

	}

	@Override
	public List<OrderItems> getOrderItems(List<Orders> orderList) {
		// TODO Auto-generated method stub
		return orderItemRepo.findByOrderIn(orderList);
	}

	@Override
	public List<OrderItems> getByOrder(Orders order) {
		// TODO Auto-generated method stub
		return orderItemRepo.findByOrder(order);
	}

}
