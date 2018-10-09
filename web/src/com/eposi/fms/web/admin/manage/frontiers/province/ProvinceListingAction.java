package com.eposi.fms.web.admin.manage.frontiers.province;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Province;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class ProvinceListingAction extends AbstractAction {
    private static final long serialVersionUID = 5904220747367364767L;

    private List<Province> items = new ArrayList<Province>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_PROVINCE_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        items = beanFmsDao.listProvince();

        return SUCCESS;
    }

    public List<Province> getItems() {
        return items;
    }
}
