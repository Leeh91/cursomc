package com.udemy.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.services.CustomerService;

@RestController
@RequestMapping(value="/customers")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Customer customer = this.customerService.getCustomer(id);
		return ResponseEntity.ok().body(customer);
	}
}
