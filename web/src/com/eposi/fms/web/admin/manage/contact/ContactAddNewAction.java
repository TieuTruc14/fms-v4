package com.eposi.fms.web.admin.manage.contact;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Contact;

/**
 * Created by TienManh on 7/22/2016.
 */
public class ContactAddNewAction extends AbstractAction {

    private static final long serialVersionUID = -7188975163851312017L;
    private String companyId;
    private Contact item;

    public String execute(){

        return SUCCESS;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Contact getItem() {
        return item;
    }

    public void setItem(Contact item) {
        this.item = item;
    }
}
