package com.eposi.fms.model;

import java.io.Serializable;

public class GroupPermission implements Serializable {
    private static final long serialVersionUID = 8178664039652922765L;

    private String     groupid;
    private String     permissionId;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
