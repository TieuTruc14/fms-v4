package com.eposi.fms.v2.model;

import java.io.Serializable;

/**
 * Created by TienManh on 7/1/2016.
 */
public class UserDevice implements Serializable {
    private static final long serialVersionUID = -5312668642054488087L;
    private long   id;
    private long   userId;
    private String vehicleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
