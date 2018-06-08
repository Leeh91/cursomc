package com.udemy.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.cursomc.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
