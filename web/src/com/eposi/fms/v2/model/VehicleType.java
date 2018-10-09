package com.eposi.fms.v2.model;

import java.io.Serializable;

public class VehicleType implements Serializable {

	private static final long serialVersionUID = 4171088747911976906L;
	private int 	id;
	private String  name = "";
	private String  iconCode;
	private String  note  ="";
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIconCode() {
		return iconCode;
	}

	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
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