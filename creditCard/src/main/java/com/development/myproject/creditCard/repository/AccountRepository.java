package com.development.myproject.creditCard.repository;

import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
