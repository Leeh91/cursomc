package com.udemy.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.City;
import com.udemy.cursomc.repositories.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    
    public List<City> findByState(Integer stateId){
        return this.cityRepository.findCities(stateId);
    }
}
