package com.udemy.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.udemy.cursomc.domain.City;

public class CityDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotEmpty(message="Campo nome não preenchido")
    @Length(min=5, max=80, message="O tamanho do nome dever ser no mínimo 5 e no máximo 80 caracteres")
    private String name;
    
    public CityDTO() {
        // TODO Auto-generated constructor stub
    }
    
    public CityDTO(City city){
        this.id = city.getId();
        this.name = city.getName();
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
