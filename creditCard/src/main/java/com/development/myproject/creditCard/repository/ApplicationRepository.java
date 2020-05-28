package com.development.myproject.creditCard.repository;

import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.Registration;

public interface ApplicationRepository  extends CrudRepository<Registration,Integer> {

}
