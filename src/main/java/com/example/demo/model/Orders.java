package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Order_id;
	private LocalDateTime  Order_date;
	private double Total_amount;
	private  String Order_status;//PLACED, SHIPPED, DELIVERED
	@ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
	private String address;
	
	public int getOrder_id() {
		return Order_id;
	}
	public void setOrder_id(int order_id) {
		Order_id = order_id;
	}
	public LocalDateTime getOrder_date() {
		return Order_date;
	}
	public void setOrder_date(LocalDateTime order_date) {
		Order_date = order_date;
	}
	public double getTotal_amount() {
		return Total_amount;
	}
	public void setTotal_amount(double total_amount) {
		Total_amount = total_amount;
	}
	public String getOrder_status() {
		return Order_status;
	}
	public void setOrder_status(String order_status) {
		Order_status = order_status;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Orders [Order_id=" + Order_id + ", Order_date=" + Order_date + ", Total_amount=" + Total_amount
				+ ", Order_status=" + Order_status + ", user=" + user + ", address=" + address + "]";
	}
	
	


}
