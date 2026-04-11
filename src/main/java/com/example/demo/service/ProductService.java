package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.model.Product;
import com.example.demo.model.Wishlist;

public interface ProductService {
	List<Product> getFashionByCatagory(String category);

	List<Product> getFashionByType(String type);

	Product getFashionById(int id);

	List<Product> getProductDetails(List<Wishlist> wishlist);

	List<Product> filterProducts(double min, double max, List<String> colors, String catagory);

	List<Product> filterProductsBySize(double min, double max, List<String> colors, List<String> size, String catagory);

	List<Product> getAllProducts();

	void saveProducts(Product product);

	void editProductById(Product product);

	void deleteFashionById(int id);

	long getTotalProducts();

	Page<Product> GirlsProducts(int page, int size);

	Page<Product> NewbornProducts(int page, int size);

	Page<Product> BoysProducts(int page, int size);

	Page<Product> getFashionByType(String type, int page, int size);

}
