package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Driver implements Serializable {
	private static final long serialVersionUID = -8013297862840512767L;

    private String id; // driver ky
    private String name;
    private String phone;
    private String licenceKey;//licence driver
    private Date   licenceDay;// Ngay cap bang
    private Date   licenceExp;//Ngay het han
    private int    autogen_key;

    private String aggregation;


    private Company company;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public Date getLicenceDay() {
        return licenceDay;
    }

    public void setLicenceDay(Date licenceDay) {
        this.licenceDay = licenceDay;
    }

    public Date getLicenceExp() {
        return licenceExp;
    }

    public void setLicenceExp(Date licenceExp) {
        this.licenceExp = licenceExp;
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }

    public int getAutogen_key() {
        return autogen_key;
    }

    public void setAutogen_key(int autogen_key) {
        this.autogen_key = autogen_key;
    }
}
