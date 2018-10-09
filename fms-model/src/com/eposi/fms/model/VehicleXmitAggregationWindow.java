package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class VehicleXmitAggregationWindow implements Serializable {
    private static final long serialVersionUID = 1752736131160411423L;

    private String id;
    private String vehicleAggregation;
    private Date   date;
    private long   msgCount;

    // Lost GPS
    private int    gpsNoSignalCount;
    private long   gpsNoSignalDuration;

    // Lost Data
    private int  dataNoSignalCount;
    private long dataNoSignalDuration;

    private String company;
    private String companyAggregationId;
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleAggregation() {
        return vehicleAggregation;
    }

    public void setVehicleAggregation(String vehicleAggregation) {
        this.vehicleAggregation = vehicleAggregation;
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

    public String getCompanyAggregationId() {
        return companyAggregationId;
    }

    public void setCompanyAggregationId(String companyAggregationId) {
        this.companyAggregationId = companyAggregationId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
