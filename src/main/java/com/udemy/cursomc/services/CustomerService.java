package com.udemy.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer getCustomer(Integer id) {
		Optional<Customer> customer = this.customerRepository.findById(id);
		
		return customer.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Customer.class.getName()));
	}
}
