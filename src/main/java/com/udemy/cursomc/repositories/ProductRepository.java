package com.udemy.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

/*	@Query("SELECT DISTINCT product FROM Product product INNER JOIN product.categories categories "
			+ "WHERE product.name LIKE %:name% AND categories IN :categories")
	Page<Product> search(@Param("name") String name, @Param("categories") List<Category> listCategories, Pageable pageRequest);*/
	Page<Product> findDistinctByNameContainingAndCategoriesIn(String name, List<Category> listCategories, Pageable pageRequest);
}
