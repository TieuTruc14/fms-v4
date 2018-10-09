package com.eposi.fms.web.model;

import java.io.Serializable;

/**
 * Created by Tuan on 2/2/2016.
 */
public class Point implements Serializable {
    private static final long serialVersionUID = -2065273066119098586L;

    private long unixTime;
    private double x;
    private double y;
    private int speed;

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
