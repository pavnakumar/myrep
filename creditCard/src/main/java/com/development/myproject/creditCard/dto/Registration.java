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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;




/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Registration.findAll", query="SELECT t FROM Registration t")
public class Registration {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int registrationId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer; 
	
    private int facilityTemplateId;
    
	private String status;
	
	private double proposedLimit ;
	
	@Type(type="yes_no")
	private boolean  allowCashLimit ;
	
	@OneToOne
	@JoinColumn(name="id")
	private Account account; 

	

	public int getFacilityTemplateId() {
		return facilityTemplateId;
	}

	public void setFacilityTemplateId(int facilityTemplateId) {
		this.facilityTemplateId = facilityTemplateId;
	}

	public int getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(int applicationId) {
		this.registrationId = applicationId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getProposedLimit() {
		return proposedLimit;
	}

	public void setProposedLimit(double proposedLimit) {
		this.proposedLimit = proposedLimit;
	}

	public boolean isAllowCashLimit() {
		return allowCashLimit;
	}

	public void setAllowCashLimit(boolean allowCashLimit) {
		this.allowCashLimit = allowCashLimit;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	

}
