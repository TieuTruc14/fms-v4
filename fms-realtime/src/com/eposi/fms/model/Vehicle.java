package com.eposi.fms.model;

import java.io.Serializable;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = -5498092093615856631L;

	private String id;     // Bien so xe làm primary key
	private Company company;
    private Driver driver; // default driver
    private TransportType type;
    private Integer capacity;
    private int    speedLimit;
    private String konexyId;

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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getKonexyId() {
        return konexyId;
    }

    public void setKonexyId(String konexyId) {
        this.konexyId = konexyId;
    }

}
