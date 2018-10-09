package com.eposi.fms.v2.model;

import java.io.Serializable;

public class Province implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2813893758240102852L;
	private int id;
	private String name;
	
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
}