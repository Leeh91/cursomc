package com.udemy.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.TicketPayment;

@Service
public class TicketService {
	
	public void fillPaymentTicket(TicketPayment ticket, Date orderDate) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(orderDate);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		
		ticket.setPaymentDate(calendar.getTime());
	}
}
