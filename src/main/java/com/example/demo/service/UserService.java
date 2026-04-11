package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Users;

public interface UserService {
	Users saveUser(Users u);
	Users updateUserPassword(Users u);

	/* List<Users> loginUser(String email,String password); */
	Users getUserDetailsById(int id);
	void editUserProfileById(Users u);
	List<Users> getUserDetails();
	long getTotalUsers();
	Users getUserByEmail(String email);
	/*
	 * Users findByEmail(String email); Users getUserByEmail(String email);
	 */
	

}
