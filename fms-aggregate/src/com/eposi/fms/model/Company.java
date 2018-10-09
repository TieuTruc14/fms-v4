package com.eposi.fms.model;

import java.io.Serializable;

public class Company implements Serializable {
	private static final long serialVersionUID = -3001762772506182392L;

	private String id;
	private String name;
	private int companyCount;
	private int vehicleCount;
	private int driverCount;
	private Company parent;
	private String konexyId; //activity
	private String aggregation;//Summary report

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCompanyCount() {
		return companyCount;
	}

	public void setCompanyCount(int companyCount) {
		this.companyCount = companyCount;
	}

	public int getVehicleCount() {
		return vehicleCount;
	}

	public void setVehicleCount(int vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	public int getDriverCount() {
		return driverCount;
	}

	public void setDriverCount(int driverCount) {
		this.driverCount = driverCount;
	}

	public Company getParent() {
		return parent;
	}

	public void setParent(Company parent) {
		this.parent = parent;
	}

	public String getKonexyId() {
		return konexyId;
	}

	public void setKonexyId(String konexyId) {
		this.konexyId = konexyId;
	}

	public String getAggregation() {
		return aggregation;
	}

	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}

}
