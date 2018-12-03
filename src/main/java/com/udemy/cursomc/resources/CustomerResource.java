package com.udemy.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.dto.CustomerDTO;
import com.udemy.cursomc.dto.NewCustomerDTO;
import com.udemy.cursomc.resources.exception.DataIntegrityException;
import com.udemy.cursomc.services.CustomerService;

@RestController
@RequestMapping(value="/customers")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Customer> find(@PathVariable Integer id){
		Customer customer = this.customerService.getCustomer(id);
		return ResponseEntity.ok().body(customer);
	}
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Customer> find(@RequestParam(value="email") String email){
	    Customer customer = this.customerService.findByEmail(email);
	    return ResponseEntity.ok().body(customer);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody NewCustomerDTO customerDTO){
		
		Customer customer = this.customerService.insert(this.customerService.fromDTO(customerDTO));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@Valid @RequestBody CustomerDTO customerDTO, @PathVariable Integer id){
			
		Customer customer = this.customerService.fromDTO(customerDTO);
		customer.setId(id);
		customer = this.customerService.update(customer);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Customer> delete(@PathVariable Integer id){
		
		try {
			this.customerService.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CustomerDTO>> findAll(){
		List<Customer> customers = this.customerService.findAll();
		List<CustomerDTO> categoriesDTO = customers.stream().map(category -> new CustomerDTO(category))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriesDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CustomerDTO>> findWithPagination(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="lines", defaultValue="24") Integer lines, 
			@RequestParam(value="orderBy", defaultValue="name") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Customer> customers = this.customerService.findWithPagination(page, lines, orderBy, direction);
		Page<CustomerDTO> customersDTO = customers.map(customer -> new CustomerDTO(customer));
		return ResponseEntity.ok().body(customersDTO);
	}
	
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile multipartFile){
		URI uri = this.customerService.uploadProfilePicture(multipartFile);
		return ResponseEntity.created(uri).build();		
	}
}
