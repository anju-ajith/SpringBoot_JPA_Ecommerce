package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.OrderItems;
import com.example.demo.model.Orders;
import com.example.demo.model.Users;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderItemsServiceImpl;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	private final OrderService orderService;
	private final OrderItemService orderItemService;
	private final UserService userService;



	public OrderController(OrderService orderService, OrderItemService orderItemService, UserService userService) {
		super();
		this.orderService = orderService;
		this.orderItemService = orderItemService;
		this.userService = userService;
	}

	@GetMapping("/placeOrder")
	public String placeOrder(Authentication authentication) {

		String email = authentication.getName();
		Users user = userService.getUserByEmail(email);

		if (user == null) {
			return "redirect:/userLoginPage";
		}
		orderService.placeOrder(user);

		return "redirect:/orderSuccess";

	}

	@GetMapping("/userOrderHistory")
	public String orderHistory(Authentication authentication, Model m) {
		String email = authentication.getName();
		Users user = userService.getUserByEmail(email);
		List<Orders> orderList = orderService.getorderDetails(user);
		List<OrderItems> orderItems = orderItemService.getOrderItems(orderList);

		/*
		 * System.out.println("orderList"+orderList);
		 * System.out.println("orderItems"+orderItems);
		 */

		m.addAttribute("orders", orderList);
		m.addAttribute("orderItems", orderItems);
		return "order-history";
	}

	@GetMapping("/getOrderList")
	public String getAllOrders(Model m) {

		List<Orders> orderList = orderService.getAllOrderDetails();
		m.addAttribute("orders", orderList);
		return "admin/orderList";
	}

	@PostMapping("/updateOrderStatus")
	public String updateOrderStatus(@RequestParam("order_id") int orderId,
			@RequestParam("order_status") String status) {

		Orders order = orderService.getOrderById(orderId);

		if (order != null) {

			String currentStatus = order.getOrder_status();

			if ("delivered".equalsIgnoreCase(currentStatus)) {
				return "redirect:/getOrderList";
			}

			order.setOrder_status(status);

			orderService.updateOrderStatus(order);
		}

		return "redirect:/getOrderList";
	}

	@GetMapping("/viewOrderDetails/{orderId}")
	public String orderDetails(@PathVariable int orderId, Model m) {
		Orders order = orderService.getOrderById(orderId);
		List<OrderItems> orderItems = orderItemService.getByOrder(order);

		m.addAttribute("order", order);
		m.addAttribute("orderItems", orderItems);

		return "admin/userOrderDetails";

	}
	@GetMapping("/viewOrderDetailsByCustId/{custId}")
	public String custOrderDetails(@PathVariable int custId, Model m) {
		Users user= userService.getUserDetailsById(custId);
		List<Orders> orderList= orderService.getorderDetails(user);
		m.addAttribute("orders", orderList);
		return "admin/customerDetails";
		
	}

}
