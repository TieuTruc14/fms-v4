package com.eposi.fms.v2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TienManh on 6/22/2016.
 */
public class DeviceContract implements Serializable {
    private static final long serialVersionUID = 3638305293154939062L;

    private Long id;
    private Device device;
    private Date timeCreate;
    private Date timeUpdate;
    private Date timeStart;
    private Date timeEnd;
    private int numMonth; // thoi gian dong phi (thang)
    private int status = 1;
    private String note;
    private int no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Date getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Date timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Date getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Date timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getNumMonth() {
        return numMonth;
    }

    public void setNumMonth(int numMonth) {
        this.numMonth = numMonth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
