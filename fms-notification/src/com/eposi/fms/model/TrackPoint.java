package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tuan on 8/2/2016.
 */
public class TrackPoint implements Serializable {
    private static final long serialVersionUID = 4387556096029928863L;
    private int unixTime;
    private double  x;
    private double  y;
    private int speed;

    public int getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(int unixTime) {
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
