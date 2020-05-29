package com.development.myproject.creditCard.delegator;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Card;
import com.development.myproject.creditCard.dto.FacilityTemplate;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.dto.Transaction;
import com.development.myproject.creditCard.enums.Status;
import com.development.myproject.creditCard.exception.ExceptionHandler;
import com.development.myproject.creditCard.repository.AccountRepository;
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
	
	@Resource
	private List<IAccountService> accounts;
	
	@Resource
	private CardRepository cardRepository;
	
	@Resource
	private AccountRepository accountRepository;
	
	
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
	
   
	@Transactional
	public Account doTransaction(Account account) {
		String type = getAccountType(account.getFacilityTemplateId());
		IAccountService accountService = getAccountServiceByType(type);
		if (CommonUtil.hasAvalue(accountService) && CommonUtil.hasAvalue(account) && CommonUtil.hasAvalue(account.getCard())) {
			List<Transaction> transactions = account.getCard().getTransactions();
			Card card = accountService.doTransaction(account.getCard(), transactions.get(0));
		    Optional<Card> cardData = cardRepository.findById(card.getCardId());
		    if(cardData.isPresent()) {
		    	cardData.get().setTransactions(transactions);
		    	account.setCard(cardData.get());
		    	return account;
		    }else {
		    	throw new ExceptionHandler("Invalid transaction ");
		    }

		} else {
			throw new ExceptionHandler("Invalid transaction ");
		}

	}
	@Transactional
	public void runEOMJOB() {
	    List<Account> accounts = accountRepository.getAccountsByDate(Status.ACTIVE.getStatus(),CommonUtil.getDateByGivenDays(-1),new Date());
	    if(CommonUtil.hasAvalue(accounts) && accounts.size()>0) {
	    	 for (Account account : accounts) {
	 	    	String type = getAccountType(account.getFacilityTemplateId());
	 			IAccountService accountService = getAccountServiceByType(type);
	 			if (CommonUtil.hasAvalue(accountService) && CommonUtil.hasAvalue(account) && CommonUtil.hasAvalue(account.getCard())) {
	 				try {
		 				accountService.doEOMProcess(account);
	 				}catch (Exception e) {
	 					e.printStackTrace();
	 					account.setEodProcessed(false);
					}

	 			} else {
	 				throw new ExceptionHandler("Invalid transaction ");
	 			}

	 		}
	    }
	}
	
	
}
