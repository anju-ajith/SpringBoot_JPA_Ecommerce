package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByCategory(String category);

	List<Product> findByType(String type);

	Page<Product> findByType(String type, Pageable pageable);

	List<Product> findByCategoryAndPriceBetween(String catagory, double min, double max);

	List<Product> findByCategoryAndPriceBetweenAndColorIn(String catagory, double min, double max, List<String> colors);
	List<Product> findByCategoryAndPriceBetweenAndSizeIn(String catagory, double min, double max, List<String> size);
	

	@Query("SELECT p FROM Product p WHERE " + "p.price BETWEEN :min AND :max "
			+ "AND (:size IS NULL OR p.size IN :size) " + "AND (:color IS NULL OR p.color IN :color)"
			+ "AND(p.category=:category)")
	List<Product> filterProduct( @Param("category") String category, @Param("min") Double min, @Param("max") Double max,
			@Param("color") List<String> color, @Param("size") List<String> size);

	long count();

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByCategory(String category, Pageable pageable);

}
