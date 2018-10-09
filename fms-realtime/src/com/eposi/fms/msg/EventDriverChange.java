package com.eposi.fms.msg;

import java.io.Serializable;
import java.util.Date;

public class EventDriverChange implements Serializable {
    private static final long serialVersionUID = -1568220302831738594L;

    private String prevDriver = null;
    private String newDriver = null;

    private Date datetime = null;

    private String vehicle = null;

    private float x;
    private float y;
    private String address = null;


    public String getPrevDriver() {
        return prevDriver;
    }

    public void setPrevDriver(String prevDriver) {
        this.prevDriver = prevDriver;
    }

    public String getNewDriver() {
        return newDriver;
    }


    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setNewDriver(String newDriver) {
        this.newDriver = newDriver;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
