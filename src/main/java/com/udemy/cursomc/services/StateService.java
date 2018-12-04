package com.udemy.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.State;
import com.udemy.cursomc.repositories.StateRepository;

@Service
public class StateService {
    
    @Autowired
    private StateRepository stateRepository;
    
    public List<State> findAll(){
       return this.stateRepository.findAllByOrderByName();
    }

}
