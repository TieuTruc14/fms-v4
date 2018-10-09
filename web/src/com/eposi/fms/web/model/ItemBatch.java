package com.eposi.fms.web.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tuan on 2/1/2016.
 */
public class ItemBatch implements Serializable {
    private static final long serialVersionUID = 6150545705710383870L;
    private String vehicle;
    private String devive;
    private String driver;
    private int unixTime;
    private int a0;
    private int a1;

    private int i0;
    private int i1;
    private int i2;
    private int i3;
    private int i4;
    private int i5;
    private List<Point> points;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDevive() {
        return devive;
    }

    public void setDevive(String devive) {
        this.devive = devive;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(int unixTime) {
        this.unixTime = unixTime;
    }

    public int getA0() {
        return a0;
    }

    public void setA0(int a0) {
        this.a0 = a0;
    }

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public int getI0() {
        return i0;
    }

    public void setI0(int i0) {
        this.i0 = i0;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
    }

    public int getI3() {
        return i3;
    }

    public void setI3(int i3) {
        this.i3 = i3;
    }

    public int getI4() {
        return i4;
    }

    public void setI4(int i4) {
        this.i4 = i4;
    }

    public int getI5() {
        return i5;
    }

    public void setI5(int i5) {
        this.i5 = i5;
    }

}
