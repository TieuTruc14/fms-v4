package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class XmitAggregationWindow implements Serializable{
    private static final long serialVersionUID = 2921766343439219161L;

    private String id;
    private Date   date;

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
