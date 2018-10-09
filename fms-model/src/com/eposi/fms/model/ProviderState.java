package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class ProviderState implements Serializable {
    private static final long serialVersionUID = -8922960860345689018L;
    private String providerCode;
    private Date date = new Date(); // summary date, default is today

//    private int deviceCount; // total number of devices from this provider

//    private ProviderXmitDaySum xmit;

	private long lastUpdateToCache = 0;

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getLastUpdateToCache() {
        return lastUpdateToCache;
    }

    public void setLastUpdateToCache(long lastUpdateToCache) {
        this.lastUpdateToCache = lastUpdateToCache;
    }

//    public ProviderXmitDaySum getXmit() {
//        return xmit;
//    }
//
//    public void setXmit(ProviderXmitDaySum xmit) {
//        this.xmit = xmit;
//    }

    //    public HashSet<String> getListVehicleXmit() {
//        return listVehicleXmit;
//    }
//
//    public void setListVehicleXmit(HashSet<String> listVehicleXmit) {
//        this.listVehicleXmit = listVehicleXmit;
//    }

//    public int getDeviceCount() {
//        return deviceCount;
//    }
//
//    public void setDeviceCount(int deviceCount) {
//        this.deviceCount = deviceCount;
//    }
}
