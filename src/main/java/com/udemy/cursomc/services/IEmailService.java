package com.udemy.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.udemy.cursomc.domain.Order;

public interface IEmailService {

	public void sendOrderConfirmationEMail(Order order);
	
	public void sendEMail(SimpleMailMessage message);
}
