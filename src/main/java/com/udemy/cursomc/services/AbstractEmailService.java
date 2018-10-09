package com.udemy.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.domain.Order;

public abstract class AbstractEmailService implements IEmailService {

	@Value("${cursomvc.mail.default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplateOrder(Order order) {
		Context context = new Context();
		context.setVariable("order", order);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Order order) {
		MimeMessage message;
		try {
			message = prepareMimeMessageFromOrder(order);
			sendHtmlEmail(message);
		} catch (MessagingException e) {
			sendOrderConfirmationEMail(order);
		}
		
	}

	protected MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		
		messageHelper.setTo(order.getCustomer().getEmail());
		messageHelper.setFrom(sender);
		messageHelper.setSubject("Pedido Confirmado! Código: " + order.getId());
		messageHelper.setSentDate(new Date(System.currentTimeMillis()));
		messageHelper.setText(this.htmlFromTemplateOrder(order), true);
		
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Customer customer, String newPassword) {
		SimpleMailMessage message = prepareNewPasswordEmail(customer, newPassword);
		sendEMail(message);
	}
	
	protected SimpleMailMessage  prepareNewPasswordEmail(Customer customer, String newPassword) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(customer.getEmail());
		message.setFrom(this.sender);
		message.setSubject("Solicitação de nova senha");
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText("Nova senha: " + newPassword);
		
		return message;
	}

}
