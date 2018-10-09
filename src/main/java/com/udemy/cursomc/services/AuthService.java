package com.udemy.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;


@Service
public class AuthService {
	
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private IEmailService emailService;
	
	private Random rand = new Random();

	public void sendNewPassword(String email) {
		
		Customer customer = this.customerRepository.findByEmail(email);
		
		if(customer == null) {
			throw new ObjectNotFoundException("E-mail não encontrado!");
		}
		
		String newPassword = this.newPassword();
		
		customer.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		this.customerRepository.save(customer);
		
		this.emailService.sendNewPasswordEmail(customer, newPassword);
		
	}
	
	private String newPassword() {
		char[] vet = new char[10];
		
		for(int i=0; i<10; i++) {
			vet[i] = this.randomChar();
		}
		
		return new String(vet);
	}
	
	private char randomChar() {
		int opt = rand.nextInt(3);
		
		switch (opt) {
		case 0:
			return (char)(rand.nextInt(10) + 48);
		case 1:
			return (char)(rand.nextInt(26) + 65);
		default:
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
