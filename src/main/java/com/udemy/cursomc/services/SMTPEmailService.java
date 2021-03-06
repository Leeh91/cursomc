package com.udemy.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SMTPEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SMTPEmailService.class);
	
	@Override
	public void sendEMail(SimpleMailMessage message) {
		LOG.info("Simulando envio de e-mail...");
		mailSender.send(message);
		LOG.info("E-mail enviado!");
	}

	@Override
	public void sendHtmlEmail(MimeMessage message) {
		LOG.info("Simulando envio de e-mail...");
		javaMailSender.send(message);
		LOG.info("E-mail enviado!");
	}

}
