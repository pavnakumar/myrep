package com.development.myproject.creditCard.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author user
 *
 */
@Entity
@NamedQuery(name="EndOfTheMonthReport.findAll", query="SELECT e FROM EndOfTheMonthReport e")
public class EndOfTheMonthReport {
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	private long customerId; 
	
	private long accountId; 
	
	private double outstandingAmount;
	
	private double cashWithDrawAmount;
	
	private long cardId;

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public double getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public double getCashWithDrawAmount() {
		return cashWithDrawAmount;
	}

	public void setCashWithDrawAmount(double cashWithDrawAmount) {
		this.cashWithDrawAmount = cashWithDrawAmount;
	}
	

}
