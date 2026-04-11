package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	private final ProductService productService;
	

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
		
	}

	@GetMapping("/newbornFashionType")
	public String getNewBornFashionType(@RequestParam String type, Model m
	/* @RequestParam(defaultValue = "0") int page */) {

		List<Product> newbornList = productService.getFashionByType(type);
		/*
		 * int pageSize = 6; Page<Product> newbornList =
		 * productService.getFashionByType(type, page, pageSize);
		 * 
		 * m.addAttribute("products", newbornList.getContent());
		 * m.addAttribute("currentPage", page); m.addAttribute("totalPages",
		 * newbornList.getTotalPages());
		 */

		m.addAttribute("products", newbornList);

		return "shopNowNewborn";

	}

	@GetMapping("/filterProducts")
	public String filterByPrice(@RequestParam double min, @RequestParam double max,
			@RequestParam(value = "color", required = false) List<String> color, Model model) {

		List<Product> products = productService.filterProducts(min, max, color, "newborn");
		System.out.println("Selected colors-------------------------: " + color);

		model.addAttribute("products", products);

		return "shopNowNewborn";

	}

	// boys fashion filter

	@GetMapping("/filterProductsBoysFashion")
	public String filterByPriceandSize(@RequestParam double min, @RequestParam double max,
			@RequestParam(value = "color", required = false) List<String> color,
			@RequestParam(value = "size", required = false) List<String> size, Model model) {

		List<Product> products = productService.filterProductsBySize(min, max, color, size, "boys");
		System.out.println("min------" + min);
		System.out.println("max-----------" + max);
		System.out.println("color-----------" + color);
		System.out.println("size------------" + size);
		model.addAttribute("products", products);

		return "shopNowBoysFashion";

	}

	@GetMapping("/boysFashionType")
	public String getBoysFashionType(@RequestParam String type, Model m) {

		List<Product> newbornList = productService.getFashionByType(type);
		m.addAttribute("products", newbornList);

		return "shopNowBoysFashion";

	}

	@GetMapping("/girlsFashionType")
	public String getGirlsFashionType(@RequestParam String type, Model m) {

		List<Product> newbornList = productService.getFashionByType(type);
		m.addAttribute("products", newbornList);

		return "shopNowGirlsFashion";

	}

	// girls fashion Filter

	@GetMapping("/filterProductsGirlsFashion")
	public String filterByPriceandSizeGirlsFashion(@RequestParam double min, @RequestParam double max,
			@RequestParam(value = "color", required = false) List<String> color,
			@RequestParam(value = "size", required = false) List<String> size, Model model) {

		List<Product> products = productService.filterProductsBySize(min, max, color, size, "girls");

		model.addAttribute("products", products);

		return "shopNowBoysFashion";

	}

	@GetMapping("/getAllProductList")
	public String getAllProducts(Model m) {

		List<Product> productList = productService.getAllProducts();
		m.addAttribute("products", productList);

		return "admin/productList";

	}

	@PostMapping("/saveProduct")
	public String addProducts(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile file) {

		try {
			// 1. Save image to folder
			String uploadDir = "src/main/resources/static/img/shop/";

			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

			Path path = Paths.get(uploadDir + fileName);
			Files.write(path, file.getBytes());

			// 2. Save image path in DB
			product.setImageurl("/img/shop/" + fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		productService.saveProducts(product);

		return "redirect:/getAllProductList";

	}

	@GetMapping("/editProductById/{id}")
	public String editProduct(@PathVariable int id, Model m) {
		Product product = productService.getFashionById(id);
		m.addAttribute("product", product);
		return "admin/editProduct";

	}

	@PostMapping("/updateProduct")
	public String updateStudent(@ModelAttribute Product product,
			@RequestParam(value = "imageFile", required = false) MultipartFile file) {

		if (file != null && !file.isEmpty()) {

			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			String uploadDir = "src/main/resources/static/img/shop/";

			try {
				file.transferTo(new File(uploadDir + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}

			product.setImageurl("/img/shop/" + fileName);
		}

		productService.editProductById(product);

		return "redirect:/getAllProductList";
	}

	@GetMapping("/deleteProductById/{id}")
	public String deleteProduct(@PathVariable int id, Model m) {
		productService.deleteFashionById(id);
		return "redirect:/getAllProductList";

	}

	@GetMapping("/getproductDetailsPage")
	public String ProductDetailsPage(@RequestParam int id, Model m) {

		Product product = productService.getFashionById(id);
		m.addAttribute("product", product);
		return "product-Details-Page";
	}

}
