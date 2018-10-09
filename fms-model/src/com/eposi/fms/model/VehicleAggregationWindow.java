package com.eposi.fms.model;


import java.util.HashSet;

public class VehicleAggregationWindow extends AbstractAggregationWindow {
    private static final long serialVersionUID = -4313407095476167641L;
    private long msgCount;

    // Lost GPS
    private int    gpsNoSignalCount;
    private long   gpsNoSignalDuration;

    // Lost Data
    private int  dataNoSignalCount;
    private long dataNoSignalDuration;

    @Override
    public int getType() {
        return TYPE_COMPANY;
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
}
