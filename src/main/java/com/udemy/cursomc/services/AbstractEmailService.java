package com.udemy.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.udemy.cursomc.domain.Order;

public abstract class AbstractEmailService implements IEmailService {

	@Value("${cursomvc.mail.default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEMail(Order order) {
		SimpleMailMessage message = prepareSimpleMailMessageFromOrder(order);
		sendEMail(message);

	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(order.getCustomer().getEmail());
		message.setFrom(sender);
		message.setSubject("Pedido Confirmado! Código: " + order.getId());
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText(order.toString());
		return message;
	}

}
