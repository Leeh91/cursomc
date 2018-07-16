package com.udemy.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.repositories.CategoryRepository;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category getCategory(Integer id) {
		
		Optional<Category> category = this.categoryRepository.findById(id);
		
		return category.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Category.class.getName()));
	}
	
	public Category insert(Category category) {
		category.setId(null);
		return this.categoryRepository.save(category);
	}
	
	public Category update(Category category) {
		this.getCategory(category.getId());
		return this.categoryRepository.save(category);
	}
	
	public void delete(Integer id) {
		this.getCategory(id);
		this.categoryRepository.deleteById(id);
	}
}
