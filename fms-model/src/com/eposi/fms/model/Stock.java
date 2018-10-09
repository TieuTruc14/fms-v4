package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TieuTruc on 5/6/2015.
 */
public class Stock implements Serializable {
    private long id;
    private String name;
    private Company companySource;//xuất
    private Company companyDes;//nhận
    private Date   dateCreated;
    private String userCreated;
    private Date   dateUpdate;
    private String userUpdate;
    private String note;
    private int status;  //da xuat phieu chua

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompanySource() {
        return companySource;
    }

    public void setCompanySource(Company companySource) {
        this.companySource = companySource;
    }

    public Company getCompanyDes() {
        return companyDes;
    }

    public void setCompanyDes(Company companyDes) {
        this.companyDes = companyDes;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
