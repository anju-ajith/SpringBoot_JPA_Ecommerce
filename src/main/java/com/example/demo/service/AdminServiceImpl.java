package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService,UserDetailsService {
	
	private final AdminRepository adminRepo;

	public AdminServiceImpl(AdminRepository adminRepo) {
		super();
		this.adminRepo = adminRepo;
	}

	/*
	 * @Override public List<Admin> adminLogin(String username, String password) {
	 * // TODO Auto-generated method stub List<Admin> admin =
	 * adminRepo.findByUsernameAndPassword(username, password); return admin; }
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 Optional<Admin> admin = adminRepo.findByUsername(username);

	        if (admin.isEmpty()) {
	            throw new UsernameNotFoundException("Admin not found");
	        }

	        Admin a = admin.get();

	        return new org.springframework.security.core.userdetails.User(
	                a.getUsername(),
	                a.getPassword(),
	                Collections.singleton(new SimpleGrantedAuthority(a.getRole()))
	        );
	    }
	
	
	

}
