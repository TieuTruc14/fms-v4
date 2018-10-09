package com.eposi.fms.web.catalog.model;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Model;


public class CatalogModelAddNewAction extends AbstractAction {
    private static final long serialVersionUID = -191060783291536947L;

    private Model item;

    public String execute() {

        return SUCCESS;
    }

    public Model getItem() {
        return item;
    }

    public void setItem(Model item) {
        this.item = item;
    }
}
