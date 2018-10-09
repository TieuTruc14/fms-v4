package com.eposi.fms.web.catalog.fuel;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FuelType;


public class FuelTypeAddNewAction extends AbstractAction {
    private static final long serialVersionUID = 8153406416189096047L;
    private FuelType item;

    public String execute() {

        return SUCCESS;
    }

    public FuelType getItem() {
        return item;
    }

    public void setItem(FuelType item) {
        this.item = item;
    }
}
