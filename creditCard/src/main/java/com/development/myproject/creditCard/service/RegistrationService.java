package com.development.myproject.creditCard.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.development.myproject.creditCard.delegator.DelegateService;
import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Customer;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.dto.Transaction;
import com.development.myproject.creditCard.enums.Status;
import com.development.myproject.creditCard.repository.CustomerRepository;
import com.development.myproject.creditCard.repository.FacilityTemplateRepository;

@Service
public class RegistrationService {

	@Resource
	private FacilityTemplateRepository facilityTemplateRepository;

	@Resource
	private CustomerRepository customerRepository;
	
	@Resource
	private DelegateService delegateService;

	

	@Autowired
	private CreditCardAccountService cardAccountService;

	public Registration getRegistrationData() {
		Registration registration = new Registration();
		Customer customer = new Customer();
		customer.setFacilities(facilityTemplateRepository.findAll());
		registration.setCustomer(customer);
		return registration;
	}

	@Transactional
	public Account register(Registration registration) {
	
		Customer customer = registration.getCustomer();
		registration.setCreatedDate(new Date());
		registration.setStatus(Status.APPROVED.getStatus());
		registration.setCustomer(customer);
		customer.setRegistrations(Collections.singletonList(registration));
		customerRepository.save(customer);
		Account account =delegateService.createAccount(registration);
		Transaction transaction = new Transaction();
		List<Transaction> transactions  = new ArrayList<Transaction>();
		transactions.add(transaction);
		account.getCard().setTransactions(transactions);
		return account;
		
	}

}
