package com.eposi.fms.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tuan on 2/1/2016.
 */
public class TrackItem implements Serializable {
    private static final long serialVersionUID = 9020039424845191597L;

    private Date  time;
    private int   speed;
    private double x;
    private double y;
    private String address;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
}
