package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tuan on 8/2/2016.
 */
public class GeoPoi extends AbstractMessage implements IMessage,Serializable {
    private static final long serialVersionUID = 7161740902998902468L;

    private String name;
    private String address;
    private Date   timeIn;
    private Date   timeOut;
    private String driverName;
    private String vehicle;
    private double  x;
    private double  y;

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

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public long getDuration(){
        return  timeOut.getTime()- timeIn.getTime();
    }

    public int getMessageType() {
        return MESSAGE_TYPE_GEO_POI;
    }
}
