package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAllSorted() {
		List<City> entities = repository.findAll(Sort.by("name"));
		List<CityDTO> cityDto = entities.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
		return cityDto;
	}

	@Transactional
	public CityDTO insertCityDTO(CityDTO cityDto) {
		City entity = new City();
		entity.setName(cityDto.getName());
		entity = repository.save(entity);
		return new CityDTO(entity);
	}
}