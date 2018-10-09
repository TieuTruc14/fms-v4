package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Firmware implements Serializable {

    private static final long serialVersionUID = 857908594135985417L;
    private  String     id;
    private  String     dota;
    private  String     content;
    private  String     relative;
    private  String     note;
    private Date        dateCreated;
    private String      userCreated;
    private Date        dateUpdated;
    private String      userUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDota() {
        return dota;
    }

    public void setDota(String dota) {
        this.dota = dota;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }
}
