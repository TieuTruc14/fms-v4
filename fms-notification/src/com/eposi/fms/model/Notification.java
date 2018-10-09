package com.eposi.fms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan on 23/08/2016.
 */
public class Notification implements Serializable {
    private static final long serialVersionUID = 3033905582068151931L;
    private String company;
    private List<Xmit> lstXmit = new ArrayList<>();

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Xmit> getLstXmit() {
        return lstXmit;
    }

    public void setLstXmit(List<Xmit> lstXmit) {
        this.lstXmit = lstXmit;
    }
}
