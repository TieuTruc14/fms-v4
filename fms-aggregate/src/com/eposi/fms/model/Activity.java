package com.eposi.fms.model;

import java.io.Serializable;
import java.util.Date;

public class Activity extends AbstractMessage implements IMessage, Serializable {
    private static final long serialVersionUID = 8632584614310971774L;
    private String actorName;
    private String actionName;
    private String objectId;
    private String objectName;
    private String indirectObjectName;
    private Date date;
    private String context;
    private String icon = "";
    private boolean passive = false;


    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getIndirectObjectName() {
        return indirectObjectName;
    }

    public void setIndirectObjectName(String indirectObjectName) {
        this.indirectObjectName = indirectObjectName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMessageType() {
        return MESSAGE_TYPE_ACTIVITY;
    }
}
