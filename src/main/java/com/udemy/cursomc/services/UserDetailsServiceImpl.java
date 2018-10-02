package com.udemy.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = this.customerRepository.findByEmail(email);
		
		if(customer == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(customer.getId(), customer.getEmail(), customer.getPassword(), customer.getProfiles());
	}

}
