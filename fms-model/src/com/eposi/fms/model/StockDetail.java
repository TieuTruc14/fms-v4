package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TieuTruc on 5/6/2015.
 */
public class StockDetail implements Serializable {
    private Stock stock;
    private Device device;
    private Date dateCreated;
    private User userCreated;
    private Date dateStartDevice;//thoi gian bat dau bao hanh
    private Date dateEndDevice;//ket thuc bao hanh

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateStartDevice() {
        return dateStartDevice;
    }

    public void setDateStartDevice(Date dateStartDevice) {
        this.dateStartDevice = dateStartDevice;
    }

    public Date getDateEndDevice() {
        return dateEndDevice;
    }

    public void setDateEndDevice(Date dateEndDevice) {
        this.dateEndDevice = dateEndDevice;
    }
}
