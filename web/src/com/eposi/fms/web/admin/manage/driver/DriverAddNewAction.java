package com.eposi.fms.web.admin.manage.driver;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Driver;

public class DriverAddNewAction extends AbstractAction {
    private static final long serialVersionUID = 4209155114767954217L;

    private String companyId;
    private Driver item;

    public String execute() {
        return SUCCESS;
	}

    public String getEditMode() {
        return "addnew.save";
    }

    public Driver getItem() {
        return item;
    }

    public void setItem(Driver item) {
        this.item = item;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}
