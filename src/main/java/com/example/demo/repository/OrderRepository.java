package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Orders;
import com.example.demo.model.Users;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

	@Query("SELECT o FROM Orders o WHERE o.user = :user ORDER BY o.Order_date DESC")
	List<Orders> findByUser(@Param("user") Users user);

	long count();

	@Query("SELECT SUM(o.Total_amount) FROM Orders o WHERE o.Order_status = 'Delivered'")
	Double getTotalRevenue();

	@Query("SELECT MONTH(o.Order_date), SUM(o.Total_amount) " + "FROM Orders o WHERE o.Order_status = 'Delivered' "
			+ "GROUP BY MONTH(o.Order_date)")
	List<Object[]> getMonthlyRevenue();
}
