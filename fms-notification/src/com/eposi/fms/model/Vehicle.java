package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = -5498092093615856631L;

	private String id;
	private Company company;

    //Config
	public Vehicle() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
