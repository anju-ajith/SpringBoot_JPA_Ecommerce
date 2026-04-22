package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.OrderItems;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderItemsRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepo;
	private final OrderItemsRepository orderItemRepo;
	private final CartRepository cartRepo;
	private final ProductRepository productRepo;
	private final CartService cartService;

	public OrderServiceImpl(OrderRepository orderRepo, OrderItemsRepository orderItemRepo, CartRepository cartRepo,
			ProductRepository productRepo, CartService cartService) {
		super();
		this.orderRepo = orderRepo;
		this.orderItemRepo = orderItemRepo;
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.cartService = cartService;
	}

	@Override
	@Transactional
	public void placeOrder(Users user) {

		List<Cart> cartList = cartRepo.findByUser(user);
		/* double totalAmount = 0; */
		
		//save order
		Orders order = new Orders();
		order.setUser(user);
		order.setOrder_date(LocalDateTime.now());
		order.setOrder_status("Placed");
		order.setAddress(user.getAddress());
		order.setTotal_amount(cartService.getCartTotal(cartList));
		orderRepo.save(order);
		
		
		//save order items
		for(Cart cart :cartList) {
			OrderItems items = new OrderItems();
			items.setQuantity(cart.getQuantity());
			items.setTotal(cart.getCart_total());
			 items.setPrice(cart.getProduct().getPrice());
			items.setOrder(order);
			items.setProduct(cart.getProduct());
			 orderItemRepo.save(items);
			
			 // Update Stock
	            Product p = cart.getProduct();
	            p.setStock(p.getStock() - cart.getQuantity());
	            productRepo.save(p);
	            
	            //delete form cart
	            cartRepo.deleteByUser(user);
			
			
		}
		
	}

	@Override
	public List<Orders> getorderDetails(Users user) {
		// TODO Auto-generated method stub
	List<Orders> orderList = orderRepo.findByUser(user);
	return orderList;
	}

	@Override
	public List<Orders> getAllOrderDetails() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}

	@Override
	public Orders getOrderById(int orderId) {
		// TODO Auto-generated method stub
		return orderRepo.findById(orderId).get();
	}

	@Override
	public void updateOrderStatus(Orders order) {
		// TODO Auto-generated method stub
		orderRepo.save(order);
	}

	@Override
	public long getTotalOrders() {
		// TODO Auto-generated method stub
		return orderRepo.count();
	}

	@Override
	public double calculateTotalRevenue() {
		Double revenue = orderRepo.getTotalRevenue();
		double totalRevenue = (revenue != null) ? revenue : 0.0;
		return totalRevenue;
	}

	@Override
	public List<Double> getMonthlyRevenue() {
		// TODO Auto-generated method stub
		List<Object[]> results = orderRepo.getMonthlyRevenue();
		
		System.out.println("monthly revenue-----------------"+results);
		 Double[] monthlyRevenue = new Double[12];
	    Arrays.fill(monthlyRevenue, 0.0);

	    for (Object[] row : results) {
	        int month = (int) row[0]; // 1 = Jan
	        Double revenue = (Double) row[1];
	        monthlyRevenue[month - 1] = revenue;
	    }

	    return Arrays.asList(monthlyRevenue);
		
	}


}
