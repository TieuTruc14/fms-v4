package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable {
	private static final long serialVersionUID = -3001762772506182392L;

	private String id;
	private String name;
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
