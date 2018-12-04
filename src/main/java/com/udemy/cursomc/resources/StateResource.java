package com.udemy.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.domain.City;
import com.udemy.cursomc.domain.State;
import com.udemy.cursomc.dto.CityDTO;
import com.udemy.cursomc.dto.StateDTO;
import com.udemy.cursomc.services.CityService;
import com.udemy.cursomc.services.StateService;

@RestController
@RequestMapping(value="/estados")
public class StateResource {

    @Autowired
    private StateService stateService;
    @Autowired
    private CityService cityService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<StateDTO>> findAll(){
        List<State> listState = this.stateService.findAll();
        List<StateDTO> listStateDTO = listState.stream().map(obj -> new StateDTO(obj)).collect(Collectors.toList());
        
        return ResponseEntity.ok().body(listStateDTO);
    }
    
    @RequestMapping(value="/{stateId}/cidades", method=RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> findCitiesByState(@PathVariable Integer stateId){
        List<City> listCity = this.cityService.findByState(stateId);
        List<CityDTO> listCityDTO = listCity.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());
        
        return ResponseEntity.ok().body(listCityDTO);
    }
}
