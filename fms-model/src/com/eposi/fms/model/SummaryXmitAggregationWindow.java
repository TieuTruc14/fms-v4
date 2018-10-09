package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class SummaryXmitAggregationWindow implements Serializable {
    private static final long serialVersionUID = 702393406105541960L;

    private String id; // Mã số của Đơn vị cung cấp
    private Date date = new Date();
    private long msgCount;

    // Lost GPS
    private int    gpsNoSignalCount;
    private long   gpsNoSignalDuration;

    // Lost Data
    private int  dataNoSignalCount;
    private long dataNoSignalDuration;

    private String company;

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

    public long getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(long msgCount) {
        this.msgCount = msgCount;
    }

    public int getGpsNoSignalCount() {
        return gpsNoSignalCount;
    }

    public void setGpsNoSignalCount(int gpsNoSignalCount) {
        this.gpsNoSignalCount = gpsNoSignalCount;
    }

    public long getGpsNoSignalDuration() {
        return gpsNoSignalDuration;
    }

    public void setGpsNoSignalDuration(long gpsNoSignalDuration) {
        this.gpsNoSignalDuration = gpsNoSignalDuration;
    }

    public int getDataNoSignalCount() {
        return dataNoSignalCount;
    }

    public void setDataNoSignalCount(int dataNoSignalCount) {
        this.dataNoSignalCount = dataNoSignalCount;
    }

    public long getDataNoSignalDuration() {
        return dataNoSignalDuration;
    }

    public void setDataNoSignalDuration(long dataNoSignalDuration) {
        this.dataNoSignalDuration = dataNoSignalDuration;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
