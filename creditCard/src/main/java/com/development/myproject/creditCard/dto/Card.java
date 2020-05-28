package com.development.myproject.creditCard.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.development.myproject.creditCard.enums.util.AccountNumberGenerator;
@Entity
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long cardId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date validTo;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer; 
	
	@OneToOne
	@JoinColumn(name="account_id")
	private Account account; 
	
	private String cvv;
	
	private String cardNo;
	
	private String cardType;
	
	private double creditLimit;
	
	private double cashLimit;
	
	private double cashBalance;

	private double balance;
	
	private String cardPin;
	
	public String getCardPin() {
		return cardPin;
	}


	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}

	@Transient
	private List<Transaction> transactions;
	
	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}

	private long OldcardId;
	
	private String  status;
	 
	@PostPersist
    private void onInsert() {
    	this.cardNo = AccountNumberGenerator.GENERATE_CREDIT_CARD_DETAILS.accountGenerator(this.cardId, 13);
    }
	
	
	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public double getCashLimit() {
		return cashLimit;
	}

	public void setCashLimit(double cashLimit) {
		this.cashLimit = cashLimit;
	}

	public long getOldcardId() {
		return OldcardId;
	}

	public void setOldcardId(long oldcardId) {
		OldcardId = oldcardId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	public double getCashBalance() {
		return cashBalance;
	}


	public void setCashBalance(double cashBalance) {
		this.cashBalance = cashBalance;
	}
	
	

}
