package com.eposi.fms.model;

import java.io.Serializable;

public class GroupMember implements Serializable {
    private static final long serialVersionUID = -2033905273602051911L;
    private String     groupId;
    private String     username;

    public GroupMember(){}

    public GroupMember(String groupId,String username){
        this.groupId=groupId;
        this.username=username;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
