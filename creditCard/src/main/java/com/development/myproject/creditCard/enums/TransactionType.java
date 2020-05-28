package com.development.myproject.creditCard.enums;

public enum TransactionType {
	
	CASH_WITHDRAW("CASH_WIDRAW"),
	
	DEBIT("DEBIT"),
	
	CREDIT("CREDIT"),
	
	LATE_CHARGE_INTEREST("LATE_CHARGE_INTEREST"),
	
	INTEREST_ON_CASH_WITHDRAW("INTEREST_ON_CASH_WITHDRAW"),
	
	INTEREST_ON_UN_PAID_OUTSTANDING("INTEREST_ON_UN_PAID_OUTSTANDING");

	TransactionType(String transactionType) {
		this.transactionType  = transactionType;
	}
	
	private String transactionType;
	
	public String getTransactionType() {
		return transactionType;
	}
	

}
