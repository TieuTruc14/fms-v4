package com.eposi.fms.web.catalog.firmware;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Firmware;

public class CatalogFirmwareAddNewAction extends AbstractAction {
    private static final long serialVersionUID = -191060783291536947L;

    private Firmware item;

    public String execute() {

        return SUCCESS;
    }

    public Firmware getItem() {
        return item;
    }

    public void setItem(Firmware item) {
        this.item = item;
    }
}
