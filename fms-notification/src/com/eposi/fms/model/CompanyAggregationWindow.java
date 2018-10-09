package com.eposi.fms.model;

public class CompanyAggregationWindow extends AbstractAggregationWindow {
    private static final long serialVersionUID = -2213901979735982440L;

    private long   msgCount;
    // Lost GPS
    private int    gpsNoSignalCount;
    private long   gpsNoSignalDuration;

    // Lost Data
    private int  dataNoSignalCount;
    private long dataNoSignalDuration;

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
