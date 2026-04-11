package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Wishlist;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepo;

	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public List<Product> getFashionByType(String type) {
		// TODO Auto-generated method stub
		return productRepo.findByType(type);
	}

	@Override
	public List<Product> getFashionByCatagory(String category) {
		// TODO Auto-generated method stub
		return productRepo.findByCategory(category);
	}

	@Override
	public Product getFashionById(int id) {
		// TODO Auto-generated method stub
		return productRepo.findById(id).get();
	}

	@Override
	public List<Product> getProductDetails(List<Wishlist> wishlist) {
		List<Product> products = new ArrayList<>();

		for (Wishlist w : wishlist) {
			products.add(w.getProduct());
		}

		return products;
	}

	@Override
	public List<Product> filterProducts(double min, double max, List<String> colors, String catagory) {
		// TODO Auto-generated method stub
		if (colors == null || colors.isEmpty()) {
			return productRepo.findByCategoryAndPriceBetween(catagory, min, max);
		} else {
			return productRepo.findByCategoryAndPriceBetweenAndColorIn(catagory, min, max, colors);
		}
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	public void saveProducts(Product product) {
		productRepo.save(product);

	}

	@Override
	public void editProductById(Product product) {
		boolean isTrue = productRepo.findById(product.getProduct_id()).isPresent();
		if (isTrue) {
			productRepo.save(product);
		}

	}

	@Override
	public void deleteFashionById(int id) {
		// TODO Auto-generated method stub
		productRepo.deleteById(id);
	}

	@Override
	public long getTotalProducts() {
		// TODO Auto-generated method stub
		return productRepo.count();
	}

	@Override
	public Page<Product> GirlsProducts(int page, int size) {
		// TODO Auto-generated method stub
		return productRepo.findByCategory("girls", PageRequest.of(page, size));
	}

	@Override
	public Page<Product> NewbornProducts(int page, int size) {
		// TODO Auto-generated method stub
		return productRepo.findByCategory("newborn", PageRequest.of(page, size));
	}

	@Override
	public Page<Product> BoysProducts(int page, int size) {
		// TODO Auto-generated method stub
		return productRepo.findByCategory("boys", PageRequest.of(page, size));
	}

	@Override
	public Page<Product> getFashionByType(String type, int page, int size) {
		// TODO Auto-generated method stub
		return productRepo.findByType(type, PageRequest.of(page, size));
	}

	@Override
	public List<Product> filterProductsBySize(double min, double max, List<String> colors, List<String> size,
			String catagory) {
		if ((colors == null || colors.isEmpty()) && (size == null || size.isEmpty())) {
			return productRepo.findByCategoryAndPriceBetween(catagory, min, max);
		} else if (size == null || size.isEmpty()) {

			return productRepo.findByCategoryAndPriceBetweenAndColorIn(catagory, min, max, colors);
		}else if(colors == null || colors.isEmpty()) {
			return productRepo.findByCategoryAndPriceBetweenAndSizeIn(catagory, min, max,size);
			
		}
		return productRepo.filterProduct(catagory, min, max, colors, size);
	}

	
}
