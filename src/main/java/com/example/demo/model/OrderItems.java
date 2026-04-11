package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItems {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Order_items_id;

	private int Quantity;
	private double price;
	private double total;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public int getOrder_items_id() {
		return Order_items_id;
	}

	public void setOrder_items_id(int order_items_id) {
		Order_items_id = order_items_id;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "OrderItems [Order_items_id=" + Order_items_id + ", Quantity=" + Quantity + ", price=" + price
				+ ", total=" + total + ", order=" + order + ", product=" + product + "]";
	}
	
	

}
