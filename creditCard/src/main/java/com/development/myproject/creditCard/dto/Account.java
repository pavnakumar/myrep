package com.development.myproject.creditCard.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.development.myproject.creditCard.enums.util.AccountNumberGenerator;


/**
 * The persistent class for the creditcard database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT c FROM Account c")
public class Account  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;


	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	
	@Column(nullable=false)
	private int facilityTemplateId;

	@Column
	private String accountNo;

	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date statementGenerationDate;
	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardId")
	private Card card;
	
	@OneToOne
    @JoinColumn(name = "custometId")
	private Customer customer;
	
	@PostPersist
	private void insertDate() {
		this.accountNo = AccountNumberGenerator.GENERATE_CREDIT_CARD_DETAILS.accountGenerator(this.id,6);
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


	public int getFacilityTemplateId() {
		return facilityTemplateId;
	}


	public void setFacilityTemplateId(int facilityTemplateId) {
		this.facilityTemplateId = facilityTemplateId;
	}

	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getStatementGenerationDate() {
		return statementGenerationDate;
	}


	public void setStatementGenerationDate(Date statementGenerationDate) {
		this.statementGenerationDate = statementGenerationDate;
	}


	public Card getCard() {
		return card;
	}


	public void setCard(Card card) {
		this.card = card;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	

}