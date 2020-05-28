package com.development.myproject.creditCard.delegator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Card;
import com.development.myproject.creditCard.dto.FacilityTemplate;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.dto.Transaction;
import com.development.myproject.creditCard.exception.ExceptionHandler;
import com.development.myproject.creditCard.repository.CardRepository;
import com.development.myproject.creditCard.repository.FacilityTemplateRepository;
import com.development.myproject.creditCard.service.CreditCardAccountService;
import com.development.myproject.creditCard.service.IAccountService;
import com.development.myproject.creditCard.util.CommonUtil;




@Component
public class DelegateService {

	
	@Resource
	private CreditCardAccountService cardAccountService;
	
	@Resource
	private FacilityTemplateRepository facilityTemplateRepository;
	
	@Autowired
	List<IAccountService> accounts;
	
	@Resource
	CardRepository cardRepository;
	
	
	public Account createAccount(Registration registration) {
		
	 String type  = getAccountType(registration.getFacilityTemplateId());
	   IAccountService accountService = getAccountServiceByType(type);
		if( CommonUtil.hasAvalue(accountService)) {
			
			return accountService.createAccount(registration);
			
		}else {
			throw new  ExceptionHandler("improper inputs");
		}
		
	}
	
	
	
	private String getAccountType(int facTempId) {
		Optional<FacilityTemplate> fOptional= facilityTemplateRepository.findById(facTempId);

		if(fOptional.isPresent()) {
			return fOptional.get().getType();
		}else {
			throw new  ExceptionHandler("facility not found ");
		}
	}
	
	
	private IAccountService getAccountServiceByType(String type) {
		
		if(CommonUtil.hasAvalue(accounts)) {
			for (IAccountService iAccountService : accounts) {
				
				if(type.equals(iAccountService.getType())) {
					return iAccountService;
				}
				
			}
		}
		
		return null;
	}
	
   
	public Account doTransaction(Account account) {
		String type  = getAccountType(account.getFacilityTemplateId());
		   IAccountService accountService = getAccountServiceByType(type);
		   Card card  = accountService.doTransaction(account.getCard(), account.getCard().getTransactions().get(0));
		   List<Transaction> transactions  = new ArrayList<Transaction>();
		   Transaction transaction = new Transaction();
		   Optional<Card> uiCard = cardRepository.findById(card.getCardId());
			transactions.add(transaction);
			account.setCard(uiCard.get());
			account.getCard().setTransactions(transactions);
			
			
		   return account;
		   
	}
	
	
}
