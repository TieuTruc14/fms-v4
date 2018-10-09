package com.eposi.fms.web.catalog.contract;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.ContractType;


public class CatalogContractAddNewAction extends AbstractAction {
    private static final long serialVersionUID = -191060783291536947L;

    private ContractType item;

    public String execute() {

        return SUCCESS;
    }

    public ContractType getItem() {
        return item;
    }

    public void setItem(ContractType item) {
        this.item = item;
    }
}
