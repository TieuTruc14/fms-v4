package com.eposi.fms.web.catalog.transport;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.TransportType;


public class CatalogTransportAddNewAction extends AbstractAction {
    private static final long serialVersionUID = -191060783291536947L;

    private TransportType item;

    public String execute() {

        return SUCCESS;
    }

    public TransportType getItem() {
        return item;
    }

    public void setItem(TransportType item) {
        this.item = item;
    }
}
