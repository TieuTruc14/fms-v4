package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class DriverState implements Serializable {
    private static final long serialVersionUID = -5852678905833504605L;

    private String driver; // driver license key

    // Hold the owner to avoid request to DB too much!
    private String companyOwner = null;
    private String provinceOwner = null;
    private String providerCode = null;
    private int lastSyncDb = 0; // unix time

    /***********************************************************
     * Statistics - today
     */
    private Date date; // summary date, default is today

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public String getProvinceOwner() {
        return provinceOwner;
    }

    public void setProvinceOwner(String provinceOwner) {
        this.provinceOwner = provinceOwner;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public int getLastSyncDb() {
        return lastSyncDb;
    }

    public void setLastSyncDb(int lastSyncDb) {
        this.lastSyncDb = lastSyncDb;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
