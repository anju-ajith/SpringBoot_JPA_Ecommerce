package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	List<Admin> findByUsernameAndPassword(String username,String password);
	Optional<Admin> findByUsername(String username);

}
