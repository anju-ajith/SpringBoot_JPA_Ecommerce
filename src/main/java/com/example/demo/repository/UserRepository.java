package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	/*
	 * @Query("select u from Users u where u.email = :email AND u.password = :password"
	 * ) List<Users> loginUser(@Param("email") String email, @Param("password")
	 * String password);
	 */

	long count();

	/* Users findByEmail(String email); */

	Optional<Users> findByEmail(String email);
	
}
