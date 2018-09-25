package com.udemy.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.domain.enums.CustomerType;
import com.udemy.cursomc.dto.NewCustomerDTO;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.resources.exception.FieldMessage;
import com.udemy.cursomc.services.validation.utils.BRDocumentUtils;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, NewCustomerDTO>{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public boolean isValid(NewCustomerDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<FieldMessage>();

		//TODO - Create tests
		if(dto.getType().equals(CustomerType.INDIVIDUAL.getCode()) && !BRDocumentUtils.isValidCPF(dto.getIndividualOrPartyDoc())) {
			list.add(new FieldMessage("individualOrPartyDoc", "CPF inválido"));
		}
		
		if(dto.getType().equals(CustomerType.PARTY.getCode()) && !BRDocumentUtils.isValidCNPJ(dto.getIndividualOrPartyDoc())) {
			list.add(new FieldMessage("individualOrPartyDoc", "CNPJ inválido"));
		}
		
		Customer customer = this.customerRepository.findByEmail(dto.getEmail());
		
		if(customer != null) {
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
