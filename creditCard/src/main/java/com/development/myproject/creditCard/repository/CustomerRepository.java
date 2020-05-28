package com.development.myproject.creditCard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.development.myproject.creditCard.dto.Customer;
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
