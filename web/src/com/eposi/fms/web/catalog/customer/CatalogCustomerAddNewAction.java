package com.eposi.fms.web.catalog.customer;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.CustomerType;


public class CatalogCustomerAddNewAction extends AbstractAction {
    private static final long serialVersionUID = -191060783291536947L;

    private CustomerType item;

    public String execute() {

        return SUCCESS;
    }

    public CustomerType getItem() {
        return item;
    }

    public void setItem(CustomerType item) {
        this.item = item;
    }
}
