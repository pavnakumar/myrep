package com.development.myproject.creditCard.repository;

import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.CreditProperty;

public interface CreditPropertyRepository extends CrudRepository<CreditProperty, Integer> {
	
  public CreditProperty findByName(String name);

}
