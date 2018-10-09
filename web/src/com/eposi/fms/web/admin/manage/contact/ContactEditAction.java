package com.eposi.fms.web.admin.manage.contact;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Contact;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TienManh on 7/22/2016.
 */
public class ContactEditAction extends AbstractAction{
    private static final long serialVersionUID = -5431141038649434988L;
    private String contactId;
    private String companyId;
    private Contact item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW)) return SUCCESS;
        try{
            if(contactId==null || StringUtils.isEmpty(contactId) || item==null){
                return SUCCESS;
            }
            Contact newItem=beanFmsDao.getContact(Integer.parseInt(contactId));
            if(newItem!=null){
                newItem.setName(item.getName());
                newItem.setEmail(item.getEmail());
                newItem.setPosition(item.getPosition());
                newItem.setPhone(item.getPhone());
                newItem.setNote(item.getNote());
                newItem.setDateUpdated(new Date());
                newItem.setUserUpdated(user.getUsername());

                beanFmsDao.editContact(newItem);
            }
        }catch (Exception e){
            System.out.println("Contact.edit.Error : "+e.getMessage());
            e.printStackTrace();
        }

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

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}
