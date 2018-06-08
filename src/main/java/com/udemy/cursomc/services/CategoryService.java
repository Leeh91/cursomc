package com.udemy.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.ObjectNotFoundException;
import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category getCategory(Integer id) {
		
		Optional<Category> category = this.categoryRepository.findById(id);
		
		return category.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Category.class.getName()));
	}
}