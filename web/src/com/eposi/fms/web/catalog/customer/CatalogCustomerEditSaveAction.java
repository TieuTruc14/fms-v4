package com.eposi.fms.web.catalog.customer;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.CustomerType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class CatalogCustomerEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 2131401253745105041L;
    private CustomerType item;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CUSTOMER_TYPE_EDIT)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if (item == null) {
            return SUCCESS;
        }
        if (item.getId() == null) {
            return SUCCESS;
        }
        if(beanFmsDao.getCustomerType(item.getId())==null){
            return  SUCCESS;
        }

        try {
            item.setUserUpdated(user.getUsername());
            item.setDateUpdated(new Date());
            beanFmsDao.editCustomerType(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        super.validate();
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public CustomerType getItem() {
        return item;
    }

    public void setItem(CustomerType item) {
        this.item = item;
    }
}