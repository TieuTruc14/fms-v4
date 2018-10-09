package com.eposi.fms.v2.model;

import java.io.Serializable;

public class TransportType implements Serializable {
	private static final long serialVersionUID = 5479893698316053919L;
	
	private int 	id;
	private String  name = "";
	private int     maxSpeed =80;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public String toString() {
		return getName();
	}
	
}