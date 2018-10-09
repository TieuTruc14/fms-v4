package com.eposi.fms.web.admin.dashboard;

public class Dashboard implements java.io.Serializable {
    private static final long serialVersionUID = 4423497860179165304L;

    private int branchs;
    private int companys;
    private int vehicles;
    private int drivers;

    private int vehicleInDay;
    private int vehicleInMonth;
    private int vehicleInYear;

    public int getBranchs() {
        return branchs;
    }

    public void setBranchs(int branchs) {
        this.branchs = branchs;
    }

    public int getCompanys() {
        return companys;
    }

    public void setCompanys(int companys) {
        this.companys = companys;
    }

    public int getVehicles() {
        return vehicles;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    public int getDrivers() {
        return drivers;
    }

    public void setDrivers(int drivers) {
        this.drivers = drivers;
    }

    public int getVehicleInDay() {
        return vehicleInDay;
    }

    public void setVehicleInDay(int vehicleInDay) {
        this.vehicleInDay = vehicleInDay;
    }

    public int getVehicleInMonth() {
        return vehicleInMonth;
    }

    public void setVehicleInMonth(int vehicleInMonth) {
        this.vehicleInMonth = vehicleInMonth;
    }

    public int getVehicleInYear() {
        return vehicleInYear;
    }

    public void setVehicleInYear(int vehicleInYear) {
        this.vehicleInYear = vehicleInYear;
    }
}
