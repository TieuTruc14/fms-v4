package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class DrivingOver10h extends AbstractMessage implements IMessage,Serializable {
    private static final long serialVersionUID = -4538106866543981304L;

    private String vehicle = null;
    private String typeName;
    private Date   beginTime = null;
    private double beginX;
    private double beginY;
    private Date   endTime = null;
    private double endX;
    private double endY;
    private String beginAddress;
    private String endAddress;

    // owner
    private String company;
    private String driver;
    private String licenceKey;
    private String driverName;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
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

    public String getBeginAddress() {
        return beginAddress;
    }

    public void setBeginAddress(String beginAddress) {
        this.beginAddress = beginAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public long getDuration(){
        if(beginTime==null) return  0l;
        if(endTime ==null)  return  0l;

        return endTime.getTime()- beginTime.getTime();
    }

    public int getMessageType() {
        return MESSAGE_TYPE_OVER10H;
    }
}
