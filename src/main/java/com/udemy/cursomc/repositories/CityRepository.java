package com.udemy.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.cursomc.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

}