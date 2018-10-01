package com.udemy.cursomc.domain.enums;

public enum Profile {
	ADMIN(1, "ROLE_ADMIN"),
	CUSTOMER(2, "ROLE_CUSTOMER");
	
	private int id;
	private String description;
	
	private Profile(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static Profile toEnum(Integer id) {
		if(id == null) {
			return null;
		}
		
		for(Profile profile : values()) {
			if(id.equals(profile.getId())) {
				return profile;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + id);
	}

}
