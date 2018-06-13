package com.udemy.cursomc.domain.enums;

public enum CustomerType {
	INDIVIDUAL(1, "Pessoa Física"),
	PARTY(2, "Pessoa Jurídica");
	
	private int code;
	private String description;
	
	private CustomerType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static CustomerType toEnum(Integer code) {
		for(CustomerType type: CustomerType.values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("Id do tipo de cliente inválido - [ID="+ code +"]");
	}
	
}
