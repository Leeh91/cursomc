package com.udemy.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.dto.CustomerDTO;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.resources.exception.FieldMessage;

public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO>{
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public boolean isValid(CustomerDTO customerDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)this.request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<FieldMessage>();
		
		Customer customer = this.customerRepository.findByEmail(customerDTO.getEmail());
		
		if(customer != null && !customer.getId().equals(id)) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
	

}
