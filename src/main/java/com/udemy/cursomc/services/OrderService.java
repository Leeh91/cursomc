package com.udemy.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Order;
import com.udemy.cursomc.repositories.OrderRepository;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order find(Integer id) {
		Optional<Order> order = this.orderRepository.findById(id);
		return order.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Order.class.getName()));
	}

}
