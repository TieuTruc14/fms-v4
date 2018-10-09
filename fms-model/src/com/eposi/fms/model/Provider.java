package com.eposi.fms.model;

import java.io.Serializable;

public class Provider implements Serializable {
	private static final long serialVersionUID = 2876476490700956047L;

	private int id;
	private String name;
	private String address;
	private String web;
	private String mail;
	private String tel;
	private String fax;
	private String description;

    //
    private int deviceCount = 0;
    private String amqp; // information of amqp server

    //
    private Integer vehicleCount = 0;

	public Provider() {
		super();
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getAmqp() {
        return amqp;
    }

    public void setAmqp(String amqp) {
        this.amqp = amqp;
    }

    public Integer getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Integer vehicleCount) {
        this.vehicleCount = vehicleCount;
    }
}
