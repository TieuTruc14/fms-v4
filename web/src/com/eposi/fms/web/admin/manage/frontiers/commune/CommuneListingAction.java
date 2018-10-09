package com.eposi.fms.web.admin.manage.frontiers.commune;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Commune;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TienManh on 7/20/2016.
 */
public class CommuneListingAction extends AbstractAction {

    private static final long serialVersionUID = 3604481675747157267L;
    private List<Commune> items;
    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_PROVINCE_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        items = beanFmsDao.listCommune();

        return SUCCESS;
    }

    public List<Commune> getItems() {
        return items;
    }
}
