package com.eposi.fms.reporting.atgt;

import java.io.Serializable;
import java.util.Date;

public class BatchXmit implements Serializable {
    private static final long serialVersionUID = -7236868425043114745L;
    private String id;
	private String name;
    private int  deviceCount;
    private Date date;
    private int  gpsCount;
    private long gpsDuration;
    private int  dataCount;
    private long dataDuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGpsCount() {
        return gpsCount;
    }

    public void setGpsCount(int gpsCount) {
        this.gpsCount = gpsCount;
    }

    public long getGpsDuration() {
        return gpsDuration;
    }

    public void setGpsDuration(long gpsDuration) {
        this.gpsDuration = gpsDuration;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public long getDataDuration() {
        return dataDuration;
    }

    public void setDataDuration(long dataDuration) {
        this.dataDuration = dataDuration;
    }
}
