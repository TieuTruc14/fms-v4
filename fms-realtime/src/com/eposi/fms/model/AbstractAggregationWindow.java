package com.eposi.fms.model;

import java.util.Date;

public abstract class AbstractAggregationWindow extends AbstractMessage {
    private static final long serialVersionUID = 123563007802542633L;

    private Date date;
    private String companyId;
    ///////////////////////////////////////
    // trip
    private int tripCount;
    private double tripKm;

    // stop
    private int stopCount;
    private long stopDuration; // sum, in unix time (unit seconds)

    // overspeed
    private int overspeedCount;
    private long overspeedDuration; // in unix time (unit seconds)
    private int overspeedMax;
    private double overspeedKm;
    private int[] overspeedMsgRange = {0, 0, 0, 0, 0};
    private int[] overspeedRange    = {0, 0, 0, 0, 0}; // sumDayCountLT5,sumDayCount5T10,sumDayCount10T20,sumDayCount20T35,sumDayCountGT35

    // overtime
    private int overtime4hCount;
    private int overtime10Count;

    ///////////////////////////////////////


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public int getOvertime4hCount() {
        return overtime4hCount;
    }

    public void setOvertime4hCount(int overtime4hCount) {
        this.overtime4hCount = overtime4hCount;
    }

    public int getOvertime10Count() {
        return overtime10Count;
    }

    public void setOvertime10Count(int overtime10Count) {
        this.overtime10Count = overtime10Count;
    }

    public int getOverspeedCount() {
        return overspeedCount;
    }

    public void setOverspeedCount(int overspeedCount) {
        this.overspeedCount = overspeedCount;
    }

    public long getOverspeedDuration() {
        return overspeedDuration;
    }

    public void setOverspeedDuration(long overspeedDuration) {
        this.overspeedDuration = overspeedDuration;
    }

    public int getOverspeedMax() {
        return overspeedMax;
    }

    public void setOverspeedMax(int overspeedMax) {
        this.overspeedMax = overspeedMax;
    }

    public double getOverspeedKm() {
        return overspeedKm;
    }

    public void setOverspeedKm(double overspeedKm) {
        this.overspeedKm = overspeedKm;
    }

    public int[] getOverspeedMsgRange() {
        return overspeedMsgRange;
    }

    public void setOverspeedMsgRange(int[] overspeedMsgRange) {
        this.overspeedMsgRange = overspeedMsgRange;
    }

    public int[] getOverspeedRange() {
        return overspeedRange;
    }

    public void setOverspeedRange(int[] overspeedRange) {
        this.overspeedRange = overspeedRange;
    }

    public int getStopCount() {
        return stopCount;
    }

    public void setStopCount(int stopCount) {
        this.stopCount = stopCount;
    }

    public long getStopDuration() {
        return stopDuration;
    }

    public void setStopDuration(long stopDuration) {
        this.stopDuration = stopDuration;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public double getTripKm() {
        return tripKm;
    }

    public void setTripKm(double tripKm) {
        this.tripKm = tripKm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
