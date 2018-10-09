package com.eposi.fms.web.admin.manage.grant;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Group;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TienManh on 7/15/2016.
 */
public class GroupAddSaveAction extends AbstractAction {
    private static final long serialVersionUID = 7653655561044682546L;

    private Group item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_GROUP_ADD)) {
            return SUCCESS;
        }
        try {
            if(item==null) return SUCCESS;
            item.setDateCreated(new Date());
            item.setDateUpdated(new Date());
            item.setUserCreated(user.getUsername());
            item.setUserUpdated(user.getUsername());
            beanFmsDao.addGroup(item);
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.utility.grant.GroupAddSaveAction.excute: "+e.getMessage());
        }

        return SUCCESS;
    }

    public Group getItem() {
        return item;
    }

    public void setItem(Group item) {
        this.item = item;
    }
}
