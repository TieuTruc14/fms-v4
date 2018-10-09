package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public  class VehicleAggregation implements Serializable {
    private static final long serialVersionUID = 3576015455620115855L;
    private long id;
    private String companyId;
    private int vehicleCount;
    private int inDay;
    private int inWeek;
    private int inMonth;
    private int inQuarter;
    private int inYear;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getInDay() {
        return inDay;
    }

    public void setInDay(int inDay) {
        this.inDay = inDay;
    }

    public int getInWeek() {
        return inWeek;
    }

    public void setInWeek(int inWeek) {
        this.inWeek = inWeek;
    }

    public int getInMonth() {
        return inMonth;
    }

    public void setInMonth(int inMonth) {
        this.inMonth = inMonth;
    }

    public int getInQuarter() {
        return inQuarter;
    }

    public void setInQuarter(int inQuarter) {
        this.inQuarter = inQuarter;
    }

    public int getInYear() {
        return inYear;
    }

    public void setInYear(int inYear) {
        this.inYear = inYear;
    }
}
