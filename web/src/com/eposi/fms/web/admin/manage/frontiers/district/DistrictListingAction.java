package com.eposi.fms.web.admin.manage.frontiers.district;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.District;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TienManh on 7/20/2016.
 */
public class DistrictListingAction extends AbstractAction {
    private List<District> items = new ArrayList<District>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_PROVINCE_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        items = beanFmsDao.listDistrict();

        return SUCCESS;
    }

    public List<District> getItems() {
        return items;
    }
}
