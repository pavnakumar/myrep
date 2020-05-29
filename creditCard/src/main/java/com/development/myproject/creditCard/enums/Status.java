package com.development.myproject.creditCard.enums;

public enum Status {
	
	PENDING("pending"),
	APPROVED("Approved"),
	ACTIVE("Active"),
	INACTIVE("Inactive"),
	DELINQUENT("Delinquent");
	
	Status(String status) {
		this.status = status;
	}

	private String status;
	
	public String getStatus() {
		return status;
	}
	

}
