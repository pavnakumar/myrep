package com.development.myproject.creditCard.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.FacilityTemplate;

public interface FacilityTemplateRepository extends CrudRepository<FacilityTemplate,Integer> {

	 @Override
	 List<FacilityTemplate> findAll();
	 

}
