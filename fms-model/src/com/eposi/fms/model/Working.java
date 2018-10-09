package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tuan on 4/23/2015.
 */
public class Working implements Serializable {
    private static final long serialVersionUID = 5701048173508014457L;

    private String vehicle;
    private Date beginTime; // unixTime
    private double beginX;
    private double beginY;
    private String beginAddress;
    private Date endTime;  // unixTime
    private double endX;
    private double endY;
    private String endAddress;
    private String company; // owner
    private String licenceKey;
    private String driverName;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public double getBeginX() {
        return beginX;
    }

    public void setBeginX(double beginX) {
        this.beginX = beginX;
    }

    public double getBeginY() {
        return beginY;
    }

    public void setBeginY(double beginY) {
        this.beginY = beginY;
    }

    public String getBeginAddress() {
        return beginAddress;
    }

    public void setBeginAddress(String beginAddress) {
        this.beginAddress = beginAddress;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
}
