package com.security.JwtSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.JwtSecurity.dto.Company;
import com.security.JwtSecurity.repo.CompanyRepo;

@RestController
public class CompanyController {

	@Autowired
	private CompanyRepo companyRepo;

	@PostMapping("/save")
	public Company saveCompany(@RequestBody Company company) {
		Company com = companyRepo.save(company);
		return com;
	}

	@GetMapping("/getcom")
	public List<Company> getAll() {
		return companyRepo.findAll();
	}

}
