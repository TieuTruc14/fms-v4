package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TieuTruc on 5/21/2015.
 */
public class FeedBack implements Serializable {
    private long id;
    private Company company;
    private String content;
    private String username;
    private Date   dateCreated;
    private String ipClient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }
}
