package com.udemy.cursomc.domain.enums;

public enum StatePayment {

	PENDING(1, "Pendente"),
	SETTLED(2, "Quitado"),
	CANCELLED(3, "Cancelado");
	
	private Integer id;
	private String description;
	
	private StatePayment(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public static StatePayment toEnum(Integer id) {
		for(StatePayment state: StatePayment.values()) {
			if(state.getId() == id) {
				return state;
			}
		}
		
		throw new IllegalArgumentException("Id do status do pagamento inválido - [ID="+ id +"]");
	}
	
}
