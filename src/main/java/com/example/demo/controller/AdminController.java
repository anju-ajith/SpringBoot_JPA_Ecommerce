package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Admin;
import com.example.demo.model.Product;
import com.example.demo.service.AdminService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	private final AdminService adminService;
	private final OrderService orderService;
	private final ProductService productService;
	private final UserService userService;

	

	public AdminController(AdminService adminService, OrderService orderService, ProductService productService,
			UserService userService) {
		super();
		this.adminService = adminService;
		this.orderService = orderService;
		this.productService = productService;
		this.userService = userService;
	}

	@GetMapping("/admin")
	public String getAdminPage(Model m) {
		
		
		long orderCount = orderService.getTotalOrders();
		long productCount = productService.getTotalProducts();
		long userCount = userService.getTotalUsers();
		double totalRevenue = orderService.calculateTotalRevenue();
		List<Double> monthlyRevenue = orderService.getMonthlyRevenue();
 			System.out.println("******************************"+monthlyRevenue);
 			
 		m.addAttribute("productCount", productCount);	
 		m.addAttribute("productCount", productCount);
		m.addAttribute("orderCount", orderCount);
		m.addAttribute("userCount", userCount);
		m.addAttribute("totalRevenue", totalRevenue);
		m.addAttribute("monthlyRevenue", monthlyRevenue);
		return "admin/index";

	}

	@GetMapping("/adminLogin")
	public String getLoginPage() {
		return "admin/login";
	}
	
	/*
	 * @GetMapping("/adminLogout") public String logout(HttpSession session) {
	 * session.invalidate();
	 * 
	 * return "redirect:/adminLogin"; }
	 */

	@GetMapping("/addProduct")
	public String getaddPoductPage(Model m) {
		m.addAttribute("product", new Product());
		return "admin/addProduct";
	}

	/*
	  @PostMapping("/loginAdmin") public String adminLogin(@ModelAttribute Admin
	  admin, HttpSession session) {
	 
	  List<Admin> result = adminService.adminLogin(admin.getUsername(),
	  admin.getPassword()); if (result != null && !result.isEmpty()) {
	  session.setAttribute("admin", result.get(0)); return "redirect:/admin"; }
	  session.setAttribute("error", "Your Email-Id/password is incorrect");
	  
	  return "redirect:/adminLogin";
	  
	  }
	 */

}
