package com.security.JwtSecurity.controller;

import java.util.Properties;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

	public String sendEmail(String to, String subject, String msg) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("narayana@striketru.com");
		mailSender.setPassword("Narayana$R@1308");

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");

		mailSender.setJavaMailProperties(properties);
		// setting msg & Otp to email
		msg = generateOTP();

		msg += " This is your Otp";
		// TODO Auto-generated method stub
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setFrom(mailSender.getUsername());
		mailMessage.setSubject(subject);
		mailMessage.setText(msg);
		mailSender.send(mailMessage);

		return msg;

	}

	public static String generateOTP() {

		String validChars = "0123456789";

		Random random = new Random();
		StringBuilder otp = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			int randomIndex = random.nextInt(validChars.length());
			char randomChar = validChars.charAt(randomIndex);
			otp.append(randomChar);
		}

		return otp.toString();
	}
}
