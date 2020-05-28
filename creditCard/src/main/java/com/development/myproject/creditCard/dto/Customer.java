package com.development.myproject.creditCard.dto;

import java.io.Serializable;
import javax.persistence.*;

import com.sun.istack.Nullable;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long customerId;

	private String address;


	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Nullable
	private String name;
    
	@Nullable
	private String phone;
	@Nullable
	private String emailId;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "registrationId")
	private List<Registration> registrations;
	
	@Transient
	private List<FacilityTemplate> facilityTemplates;
	
	

	public Customer() {
	}

	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}

	public List<FacilityTemplate> getFacilities() {
		return facilityTemplates;
	}

	public void setFacilities(List<FacilityTemplate> facilityTemplates) {
		this.facilityTemplates = facilityTemplates;
	}

	

}