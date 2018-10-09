package com.eposi.fms.web.catalog.customer;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.CustomerType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class CatalogCustomerDetailAction extends AbstractAction {
    private static final long serialVersionUID = 278764420622126480L;

    private String id;
    private CustomerType item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CUSTOMER_TYPE_VIEW)) return SUCCESS;
        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getCustomerType(id);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.catalog.InsuranceDetailAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerType getItem() {
        return item;
    }

    public void setItem(CustomerType item) {
        this.item = item;
    }
}
