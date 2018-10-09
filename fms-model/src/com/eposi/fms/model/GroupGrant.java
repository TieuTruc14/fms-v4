package com.eposi.fms.model;

import java.io.Serializable;

/**
 * Created by TienManh on 7/15/2016.
 */
public class GroupGrant implements Serializable {

    private static final long serialVersionUID = -7108187995320275797L;
    private String id;
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
