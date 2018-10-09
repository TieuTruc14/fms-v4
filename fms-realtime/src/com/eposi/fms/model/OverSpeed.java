package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OverSpeed extends AbstractMessage implements IMessage, Serializable {
    private static final long serialVersionUID = -616914113065700128L;

    private String vehicle;

    private double beginX;
    private double beginY;
    private String beginAddress;
    private Date beginTime;

    private double endX;
    private double endY;
    private String endAddress;
    private Date endTime;

    private int duration; // in second
    private int maxSpeed; // in kmh
    private int avgSpeed; // in kmh
    private int speedLimit;
    private List<Float> lstSpeed = new LinkedList<Float>();//List speed over
    private float distance; // in km

    private String driver;
    private String licenceKey;
    private String driverName;

    private String company;


    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(int avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Float> getLstSpeed() {
        return lstSpeed;
    }

    public void setLstSpeed(List<Float> lstSpeed) {
        this.lstSpeed = lstSpeed;
    }

    public void addSpeed(float speed){
        this.lstSpeed.add(speed);
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getOsKm(){
        return distance/1000.0f;
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

    public float getOverAvgSpeed(){
        return distance*3600/duration;
    }

    public int getMessageType() {
        return MESSAGE_TYPE_OS;
    }

}
