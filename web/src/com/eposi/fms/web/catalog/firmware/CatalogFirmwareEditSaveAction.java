package com.eposi.fms.web.catalog.firmware;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Firmware;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class CatalogFirmwareEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 2131401253745105041L;
    private Firmware item;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_FIRMWARE_EDIT)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if (item == null) {
            return SUCCESS;
        }
        if (item.getId() == null) {
            return SUCCESS;
        }
        if(beanFmsDao.getFirmware(item.getId())==null){
            return  SUCCESS;
        }

        try {
            item.setUserUpdated(user.getUsername());
            item.setDateUpdated(new Date());
            beanFmsDao.editFirmware(item);
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

    public Firmware getItem() {
        return item;
    }

    public void setItem(Firmware item) {
        this.item = item;
    }

}
