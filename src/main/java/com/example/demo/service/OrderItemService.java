package com.example.demo.service;

import java.util.List;

import com.example.demo.model.OrderItems;
import com.example.demo.model.Orders;
import com.example.demo.model.Users;

public interface OrderItemService {
  List<OrderItems>	getOrderItems(List<Orders> orderList);
  List<OrderItems> getByOrder(Orders order);

}
