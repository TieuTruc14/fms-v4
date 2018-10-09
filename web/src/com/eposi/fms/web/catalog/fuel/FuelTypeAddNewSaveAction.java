package com.eposi.fms.web.catalog.fuel;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FuelType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class FuelTypeAddNewSaveAction extends AbstractAction {
    private static final long serialVersionUID = 9081305752618926969L;

    private FuelType item;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_FUEL_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if (item == null) {
            return SUCCESS;
        }

        try {
            item.setUserCreated(user.getUsername());
            item.setUserUpdated(user.getUsername());
            item.setDateCreated(new Date());
            item.setDateUpdated(new Date());
            beanFmsDao.addFuelType(item);
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

    public FuelType getItem() {
        return item;
    }

    public void setItem(FuelType item) {
        this.item = item;
    }

}
