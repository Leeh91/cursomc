package com.udemy.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.domain.Product;
import com.udemy.cursomc.repositories.CategoryRepository;
import com.udemy.cursomc.repositories.ProductRepository;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Product find(Integer id) {
		Optional<Product> product = this.productRepository.findById(id);
		
		return product.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Product.class.getName()));
	}
	
	public Page<Product> search(String name, List<Integer> ids, 
			Integer page, Integer lines, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, lines, Direction.valueOf(direction), orderBy);
		
		List<Category> categories = this.categoryRepository.findAllById(ids);
		
		return this.productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}

}
