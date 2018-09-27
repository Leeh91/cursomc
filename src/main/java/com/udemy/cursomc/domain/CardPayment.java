package com.udemy.cursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.udemy.cursomc.domain.enums.StatePayment;

@Entity
@JsonTypeName("cardPayment")
public class CardPayment extends Payment {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer installments; 
	
	public CardPayment() {
		// TODO Auto-generated constructor stub
	}

	public CardPayment(Integer id, StatePayment state, Order order, Integer installments) {
		super(id, state, order);
		this.setInstallments(installments);
	}

	public Integer getInstallments() {
		return installments;
	}

	public void setInstallments(Integer installments) {
		this.installments = installments;
	}
	
}
