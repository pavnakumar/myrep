package com.development.myproject.creditCard.service;

import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Card;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.dto.Transaction;

public interface IAccountService {
	
	public Account createAccount(Registration registration);
	
	public Card doTransaction(Card card, Transaction transaction);
	
	public String getType();
	
	public Card closeAccount(Card card);

	
}
