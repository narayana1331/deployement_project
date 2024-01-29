package com.security.JwtSecurity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.JwtSecurity.dto.Users;


public interface UserRepo extends JpaRepository<Users, Integer> {

	@Query("select user from Users user where user.email=?1")
	Users finadByUserEmail(String email);


}
