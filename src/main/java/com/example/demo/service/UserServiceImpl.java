package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepo;
	private final BCryptPasswordEncoder encoder;

	public UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	@Override
	public Users saveUser(Users u) {
		// TODO Auto-generated method stub
		String password = u.getPassword();
		String encoderPassword = encoder.encode(password);
		u.setPassword(encoderPassword);
		u.setRole("CUSTOMER");
		return userRepo.save(u);
	}

	/*
	 * @Override public List<Users> loginUser(String email, String password) { //
	 * TODO Auto-generated method stub return userRepo.loginUser(email, password); }
	 */

	@Override
	public Users getUserDetailsById(int id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).get();
	}

	@Override
	public void editUserProfileById(Users u) {
		// TODO Auto-generated method stub
		Users existingUser = userRepo.findById(u.getUser_id()).get();
		existingUser.setName(u.getName());
		existingUser.setEmail(u.getEmail());
		existingUser.setMobile(u.getMobile());
		existingUser.setAddress(u.getAddress());
		userRepo.save(existingUser);

	}

	@Override
	public List<Users> getUserDetails() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public long getTotalUsers() {
		// TODO Auto-generated method stub
		return userRepo.count();
	}

	/*
	 * @Override public Users findByEmail(String email) { // TODO Auto-generated
	 * method stub return userRepo.findByEmail(email); }
	 * 
	 * @Override public Users getUserByEmail(String email) { // TODO Auto-generated
	 * method stub return userRepo.findByEmail(email); }
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> user = userRepo.findByEmail(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException(username);
		} else {
			Users u = user.get();
			return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(),
					Collections.singleton(new SimpleGrantedAuthority(u.getRole())));
		}

	}

	@Override
	public Users getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email).orElse(null);
	}

	@Override
	public Users updateUserPassword(Users u) {
		// TODO Auto-generated method stub
		String password = u.getPassword();
		String encoderPassword = encoder.encode(password);
		u.setPassword(encoderPassword);
		return userRepo.save(u);

	}

}
