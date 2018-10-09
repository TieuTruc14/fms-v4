package com.eposi.fms.v2.model;

import java.io.Serializable;

public class Owner implements Serializable {
	private static final long serialVersionUID = 2301897494321965381L;

	private long id;
	private String name;
	private String note;
	private int operatingStatus;
	private OrganizationUnit parent;
	private String lat;
	private String lng;
	private String img;
	private int typeOwner;
	private Province province;

	public String code; // reference id

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public OrganizationUnit getParent() {
		return parent;
	}

	public void setParent(OrganizationUnit parent) {
		this.parent = parent;
	}

	/**
	 * @return the operating_status
	 */
	public int getOperatingStatus() {
		return operatingStatus;
	}

	/**
	 * @param operating_status the operating_status to set
	 */
	public void setOperatingStatus(int operatingStatus) {
		this.operatingStatus = operatingStatus;
	}
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getTypeOwner() {
		return typeOwner;
	}

	public void setTypeOwner(int typeOwner) {
		this.typeOwner = typeOwner;
	}

	/**
	 * @return the province
	 */
	public Province getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(Province province) {
		this.province = province;
	}
}
