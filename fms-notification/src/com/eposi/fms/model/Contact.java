package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TienManh on 7/22/2016.
 */
public class Contact implements Serializable {
    private static final long serialVersionUID = -5516303125235632599L;
    private int id;
    private String name;
    private String position;
    private String phone;
    private String email;
    private boolean notify;
    private Company company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
