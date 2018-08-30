package com.udemy.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.dto.CustomerDTO;
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
	
	public Customer update(Customer customer) {
		Customer customerFound = this.getCustomer(customer.getId());
		this.updateData(customerFound, customer);
		return this.customerRepository.save(customerFound);
	}
	
	public void delete(Integer id) {
		this.getCustomer(id);
		this.customerRepository.deleteById(id);
	}
	
	public List<Customer> findAll(){
		return this.customerRepository.findAll();
	}
	
	public Page<Customer> findWithPagination(Integer page, Integer lines, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, lines, Direction.valueOf(direction), orderBy);
		return this.customerRepository.findAll(pageRequest);
	}
	
	public Customer fromDTO(CustomerDTO customerDTO) {
		return new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getEmail(), null, null);
	}
	
	private void updateData(Customer newCustomer, Customer customer) {
		newCustomer.setName(customer.getName());
		newCustomer.setEmail(customer.getEmail());
	}
}
