package com.eposi.fms.model;

import java.io.Serializable;

/**
 * Created by TienManh on 7/20/2016.
 */
public class District implements Serializable{

    private static final long serialVersionUID = 4369674970007144955L;
    private String id;
    private String name;
    private String fullName;
    private Province province;
    /**********************************************************/
    private int companyCount;
    private int vehicleCount;
    private int driverCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public int getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(int companyCount) {
        this.companyCount = companyCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getDriverCount() {
        return driverCount;
    }

    public void setDriverCount(int driverCount) {
        this.driverCount = driverCount;
    }
}
