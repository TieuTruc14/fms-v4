package com.eposi.fms.v2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TienManh on 6/28/2016.
 */
public class DriverCard implements Serializable {
    private String driverKey = "";
    private String defaultVehicle = "";

    private String name = "";
    private Date dateOfBirth;
    private String address;
    private String phone;
    private Owner owner;
    private String licenseNumber = "";
    private String type = "";
    private Date dateOfGrant;
    private Date dateExper;
    private boolean enable;
    private String note = "";
    private int autogenkeyId = 0;

    public String getDriverKey() {
        return driverKey;
    }

    public void setDriverKey(String driverKey) {
        this.driverKey = driverKey;
    }

    public String getDefaultVehicle() {
        return defaultVehicle;
    }

    public void setDefaultVehicle(String defaultVehicle) {
        this.defaultVehicle = defaultVehicle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Date getDateExper() {
        return dateExper;
    }

    public void setDateExper(Date dateExper) {
        this.dateExper = dateExper;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDateOfGrant() {
        return dateOfGrant;
    }

    public void setDateOfGrant(Date dateOfGrant) {
        this.dateOfGrant = dateOfGrant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the autogenkey_id
     */
    public int getAutogenkeyId() {
        return autogenkeyId;
    }

    /**
     * @param autogenkey_id
     *            the autogenkey_id to set
     */
    public void setAutogenkeyId(int autogenkeyId) {
        this.autogenkeyId = autogenkeyId;
    }
}
