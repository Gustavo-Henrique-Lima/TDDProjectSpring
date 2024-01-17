package com.devsuperior.bds02.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.services.CityService;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

	@Autowired
	private CityService service;

	@GetMapping
	public ResponseEntity<List<CityDTO>> findAllCitySorte() {
		List<CityDTO> cityDTO = service.findAllSorted();
		return ResponseEntity.ok(cityDTO);
	}

	@PostMapping
	public ResponseEntity<CityDTO> insertNewCity(@RequestBody CityDTO city) {
		city = service.insertCityDTO(city);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(city.getId()).toUri();
		return ResponseEntity.created(uri).body(city);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCityById(@PathVariable Long id){
		service.deleteCityById(id);
		return ResponseEntity.noContent().build();
	}
}
