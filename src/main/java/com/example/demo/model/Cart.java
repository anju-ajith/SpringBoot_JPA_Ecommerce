package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cart_id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	private double cart_total;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private int quantity;

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getCart_total() {
		return cart_total;
	}

	public void setCart_total(double cart_total) {
		this.cart_total = cart_total;
	}

	public void updateTotal() {
	    this.cart_total = this.product.getPrice() * this.quantity;
	}
	
	@Override
	public String toString() {
		return "Cart [cart_id=" + cart_id + ", user=" + user + ", cart_total=" + cart_total + ", product=" + product
				+ ", quantity=" + quantity + "]";
	}
	

}
