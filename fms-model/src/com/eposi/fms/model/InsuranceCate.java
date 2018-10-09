package com.eposi.fms.model;

import java.io.Serializable;

public class InsuranceCate  implements Serializable {
    private static final long serialVersionUID = -235736592434730530L;

    private Long id;
	private String name;
	private Double fee;
	private Double maxCompensation;
	private String note;
	private Company company;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getMaxCompensation() {
		return maxCompensation;
	}

	public void setMaxCompensation(Double maxCompensation) {
		this.maxCompensation = maxCompensation;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
