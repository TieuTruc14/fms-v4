package com.eposi.fms.model;


import java.io.Serializable;
import java.util.Date;

public class Xmit extends AbstractMessage implements IMessage,Serializable{
    private static final long serialVersionUID = 2035246978870220065L;

    private String  vehicle;
    private String  company;
    private Date    beginTime;
    private Date    endTime;
    private Date    sendTime;
    private int     type;    // 1 lost GPS, 2 lost data
    private int     counter;
    private double   x;
    private double   y;
    private String  address;


    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getX() {
        return x;
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

    public int getMessageType() {
        return MESSAGE_TYPE_XMIT;
    }

}
