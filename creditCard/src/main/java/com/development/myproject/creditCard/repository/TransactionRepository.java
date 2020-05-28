package com.development.myproject.creditCard.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction, Long>{
	
	    @Modifying
		@Query("SELECT t FROM Transaction t WHERE t.card.cardId=?1 and t.transactionDate between ?2 and ?3 and t.transactionType=?4")
		public List<Transaction> findTransaction(long cardId,Date from,Date today,String transactionType );

}
