package com.security.JwtSecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.JwtSecurity.dto.Company;

public interface CompanyRepo  extends JpaRepository<Company, Integer>{

}
