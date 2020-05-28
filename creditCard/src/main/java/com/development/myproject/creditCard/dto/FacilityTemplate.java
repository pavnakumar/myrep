package com.development.myproject.creditCard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the facility database table.
 * 
 */
@Entity
@NamedQuery(name="FacilityTemplate.findAll", query="SELECT f FROM FacilityTemplate f")
public class FacilityTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int facilityTemplateId;

	private String description;

	private String type;

	

	public FacilityTemplate() {
	}

	

	public int getFacilityTemplateId() {
		return facilityTemplateId;
	}



	public void setFacilityTemplateId(int facilityTemplateId) {
		this.facilityTemplateId = facilityTemplateId;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}