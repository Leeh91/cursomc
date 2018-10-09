package com.udemy.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.domain.Order;

public interface IEmailService {

	public void sendOrderConfirmationEMail(Order order);
	
	public void sendEMail(SimpleMailMessage message);
	
	public void sendOrderConfirmationHtmlEmail(Order order);
	
	public void sendHtmlEmail(MimeMessage message);
	
	public void sendNewPasswordEmail(Customer customer, String newPassword);
}
