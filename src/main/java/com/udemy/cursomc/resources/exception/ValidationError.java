package com.udemy.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<FieldMessage>();
	
	public ValidationError(Integer status, String message, Long timestamp) {
		super(status, message, timestamp);
		// TODO Auto-generated constructor stub
	}
	
	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName, message));
	}

	public List<FieldMessage> getErrors() {
		return this.errors;
	}

}
