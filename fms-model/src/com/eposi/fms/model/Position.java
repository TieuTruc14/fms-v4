package com.eposi.fms.model;

import com.vividsolutions.jts.geom.Geometry;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = -603975651712038819L;

    private long      id;
	private Company   company;
	private String name;
	private String address;
    private Geometry geometry;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
