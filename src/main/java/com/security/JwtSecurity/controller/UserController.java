package com.security.JwtSecurity.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.JwtSecurity.dto.OtpEntity;
import com.security.JwtSecurity.dto.Users;
import com.security.JwtSecurity.repo.UserRepo;

@RestController
public class UserController {
	@Autowired
	private UserRepo repo;
	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@GetMapping("/get")
	public List<Users> get() {
		return repo.findAll();
	}

	@PostMapping("/save/user")
	public Users save(@RequestBody Users users) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		users.setPassword(encoder.encode(users.getPassword()));

		return repo.save(users);

	}

	@PutMapping("/forgot/password")
	public ResponseEntity<String> forgotPassword(@RequestBody OtpEntity entity) {
		Users users = repo.finadByUserEmail(entity.getEmail());
		if (users.getOtp() != null) {
			System.out.println("user found");
			System.out.println(users.getOtp() + " " + entity.getOtp());
			if (users.getOtp().equals(entity.getOtp())) {
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				users.setPassword(encoder.encode(entity.getNewPassword()));
				users.setOtp(null);
				 repo.save(users);
				 return new ResponseEntity<String>("Successfully password updated",HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Enter Valid OTP",HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("Resend the OTP",HttpStatus.BAD_REQUEST);

	}

	@PostMapping("/email_sender")
	public void sendEmailService(@RequestParam String email) {
		Users users = repo.finadByUserEmail(email);
		if (users != null) {
			System.out.println("available");
			String otp = emailServiceImpl.sendEmail(email, "changing your pssword", null);
			users.setOtp(otp.substring(0, 6));
			repo.save(users);
			System.out.println("saved");
		}
	}

}
