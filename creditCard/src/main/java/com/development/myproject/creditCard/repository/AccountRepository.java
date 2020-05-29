package com.development.myproject.creditCard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	@Query("SELECT t FROM Account t WHERE t.status=?1 and t.statementGenerationDate between ?2 and ?3 ")
	public List<Account> getAccountsByDate(String status ,Date startDate,Date endate); 
	

}
