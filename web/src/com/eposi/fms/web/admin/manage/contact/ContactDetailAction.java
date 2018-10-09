package com.eposi.fms.web.admin.manage.contact;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Contact;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 7/22/2016.
 */
public class ContactDetailAction extends AbstractAction {

    private static final long serialVersionUID = 7806200436512801300L;
    private String contactId;
    private String companyId;
    private Contact item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW)) return SUCCESS;
        if (StringUtils.isNotEmpty(contactId)) {
            int id=Integer.parseInt(contactId);
            item = beanFmsDao.getContact(id);
        }

        return SUCCESS;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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
