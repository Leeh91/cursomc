package com.udemy.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.services.CategoryService;

@RestController
@RequestMapping(value="/categories/")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> list(@PathVariable Integer id) {
		
		Category category = this.categoryService.getCategory(id);
		
		return ResponseEntity.ok(category);
	}

}
