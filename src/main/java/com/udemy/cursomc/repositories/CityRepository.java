package com.udemy.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.cursomc.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM City obj WHERE obj.state.id = :stateId ORDER BY obj.name")
    List<City> findCities(@Param("stateId") Integer stateId);
}
