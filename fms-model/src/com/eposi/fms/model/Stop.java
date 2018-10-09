package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Stop implements Serializable {
	private static final long serialVersionUID = 5159822287841442683L;

	private String vehicle;
	private String vehicleAggregation;
	private Date beginTime;
	private Date endTime;
	private double x;
	private double y;
	private String address;
    private String driver;
    private String licenceKey;
    private String driverName;
    private String company;
	private String companyAggregationId;

	public double getX() {
		return x;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getVehicleAggregation() {
		return vehicleAggregation;
	}

	public void setVehicleAggregation(String vehicleAggregation) {
		this.vehicleAggregation = vehicleAggregation;
	}

	public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

	public String getCompanyAggregationId() {
		return companyAggregationId;
	}

	public void setCompanyAggregationId(String companyAggregationId) {
		this.companyAggregationId = companyAggregationId;
	}

	public long getDuration() {
        long d = 0;
        try {
            d = (endTime.getTime() - beginTime.getTime());
            d = d/1000L;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}
