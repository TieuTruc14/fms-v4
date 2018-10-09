package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Contract implements Serializable {
    private static final long serialVersionUID = -4199978843410457811L;
    private  long         id;
    private  Vehicle      vehicle;
    private  Company      company;
    private  Date         dateCreated;
    private  Date         dateUpdate;
    private  Date         dateStart;
    private  Date         dateEnd;
    private  String       note;
    private  boolean      enable=true;
    private  String       userCreated;
    private  String       userUpdated;
    private  Date         censoredDate;//th?i gian xét duy?t h?p ??ng
    private  boolean      censored;//?ã duy?t ch?a

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public Date getCensoredDate() {
        return censoredDate;
    }

    public void setCensoredDate(Date censoredDate) {
        this.censoredDate = censoredDate;
    }

    public boolean isCensored() {
        return censored;
    }

    public void setCensored(boolean censored) {
        this.censored = censored;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }
}
