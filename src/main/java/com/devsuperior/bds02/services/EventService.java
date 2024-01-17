package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional
	public EventDTO updateEvent(Long id, EventDTO entity) {
		try {
			Event event = repository.getOne(id);
			updateData(event, entity);
			event = repository.save(event);
			return new EventDTO(event);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	private void updateData(Event event, EventDTO entity) {
		City city = cityRepository.getOne(entity.getCityId());
		event.setCity(city);
		event.setDate(entity.getDate());
		event.setName(entity.getName());
		event.setUrl(entity.getUrl());
	}
}
