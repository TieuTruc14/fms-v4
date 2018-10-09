package com.eposi.fms.model;


import java.io.Serializable;
import java.util.Date;

public class DataXmit implements Serializable {
    private static final long serialVersionUID = 2035246978870220065L;

    private String vehicle;
    private String vehicleAggregation;
    private Date   beginTime;
    private Date   endTime;
    private int    type;    // 1 lost GPS, 2 lost data
    private String company;
    private String companyAggregationId;

    public long getDuration(){
        return (endTime.getTime()- beginTime.getTime())/1000;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleAggregation() {
        return vehicleAggregation;
    }

    public void setVehicleAggregation(String vehicleAggregation) {
        this.vehicleAggregation = vehicleAggregation;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        if (type == 1) {
            return "Mất GPS";
        } else {
            return "Mất tín hiệu";
        }
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
}
