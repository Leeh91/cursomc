package com.udemy.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.dto.CategoryDTO;
import com.udemy.cursomc.resources.exception.DataIntegrityException;
import com.udemy.cursomc.services.CategoryService;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> list(@PathVariable Integer id) {
		
		Category category = this.categoryService.getCategory(id);
		
		return ResponseEntity.ok(category);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Category category){
		
		category = this.categoryService.insert(category);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable Integer id){
			category.setId(id);
			category = this.categoryService.update(category);
			return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Category> delete(@PathVariable Integer id){
		
		try {
			this.categoryService.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<Category> categories = this.categoryService.findAll();
		List<CategoryDTO> categoriesDTO = categories.stream().map(category -> new CategoryDTO(category))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriesDTO);
	}

}
