package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DataBaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

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

	@Transactional
	public void deleteCityById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado: " + id);
		} catch (ConstraintViolationException e) {
			throw new DataBaseException("Violação de integridade");
		}
	}
}