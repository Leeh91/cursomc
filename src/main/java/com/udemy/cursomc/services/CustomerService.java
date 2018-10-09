	package com.udemy.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.cursomc.domain.Address;
import com.udemy.cursomc.domain.City;
import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.domain.enums.CustomerType;
import com.udemy.cursomc.domain.enums.Profile;
import com.udemy.cursomc.dto.CustomerDTO;
import com.udemy.cursomc.dto.NewCustomerDTO;
import com.udemy.cursomc.repositories.AddressRepository;
import com.udemy.cursomc.repositories.CityRepository;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.resources.exception.AuthorizationException;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;
import com.udemy.cursomc.security.UserSS;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired
	private S3Service s3Service;
	
	public Customer getCustomer(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Profile.ADMIN) && !user.getId().equals(id)) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Customer> customer = this.customerRepository.findById(id);
		
		return customer.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Customer.class.getName()));
	}
	
	@Transactional
	public Customer insert(Customer customer) {
		customer.setId(null);
		customer =  this.customerRepository.save(customer);
		this.addressRepository.saveAll(customer.getAddresses());
		
		return customer;
		
	}
	
	@Transactional
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
		return new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getEmail(), null, null, null);
	}
	
	public Customer fromDTO(NewCustomerDTO customerDTO) {
		Customer customer = new Customer(null, customerDTO.getName(), customerDTO.getEmail(), customerDTO.getIndividualOrPartyDoc(),
				CustomerType.toEnum(customerDTO.getType()), bcryptPasswordEncoder.encode(customerDTO.getPassword()));
		
		City city = this.cityRepository.getOne(customerDTO.getCityId());
		
		Address address = new Address(null, customerDTO.getAddress(), customerDTO.getNumber(), customerDTO.getAddAddress(), 
				customerDTO.getNeighborhood(), customerDTO.getZipCode(), customer, city);
		
		customer.getAddresses().add(address);
		customer.getPhones().add(customerDTO.getPhoneNumber1());
		
		if(customerDTO.getPhoneNumber2() != null) {
			customer.getPhones().add(customerDTO.getPhoneNumber2());
		}
		
		if(customerDTO.getPhoneNumber3() != null) {
			customer.getPhones().add(customerDTO.getPhoneNumber3());
		}
		
		return customer;
	}
	
	private void updateData(Customer newCustomer, Customer customer) {
		newCustomer.setName(customer.getName());
		newCustomer.setEmail(customer.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = this.s3Service.uploadFile(multipartFile);
		
		Customer customer = this.getCustomer(user.getId());
		customer.setImageURL(uri.toString());
		
		return uri;
	}
}
