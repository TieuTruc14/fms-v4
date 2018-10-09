package com.eposi.fms.model;

import java.io.Serializable;

public class Permission implements Serializable {
    private static final long serialVersionUID = 8178664039652922765L;

    private String     id;
    private String     name;
    private String     description;
    private String     groupGrant;

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

    public String getGroupGrant() {
        return groupGrant;
    }

    public void setGroupGrant(String groupGrant) {
        this.groupGrant = groupGrant;
    }
}
