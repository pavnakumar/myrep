package com.development.myproject.creditCard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.EndOfTheMonthReport;



public interface EndOfTheMonthReportRepository  extends CrudRepository<EndOfTheMonthReport, Long>{
	
	@Query("SELECT e FROM EndOfTheMonthReport e WHERE e.accountId = ?1 ORDER BY e.id DESC")
	List<EndOfTheMonthReport> findRecordByAccountId(long accountId);
}
