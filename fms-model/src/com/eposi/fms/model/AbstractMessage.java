package com.eposi.fms.model;

import java.io.Serializable;

/**
 * Created by Tuan on 7/14/2016.
 */
public abstract class AbstractMessage{
    private String id;
    private String vehicleAggregationId;
    private String driverAggregationId;
    private String companyAggregationId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleAggregationId() {
        return vehicleAggregationId;
    }

    public void setVehicleAggregationId(String vehicleAggregationId) {
        this.vehicleAggregationId = vehicleAggregationId;
    }

    public String getDriverAggregationId() {
        return driverAggregationId;
    }

    public void setDriverAggregationId(String driverAggregationId) {
        this.driverAggregationId = driverAggregationId;
    }

    public String getCompanyAggregationId() {
        return companyAggregationId;
    }

    public void setCompanyAggregationId(String companyAggregationId) {
        this.companyAggregationId = companyAggregationId;
    }
}
