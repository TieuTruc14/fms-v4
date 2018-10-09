package com.eposi.fms.web.admin.manage.grant;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Group;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Created by TienManh on 7/15/2016.
 */
public class GroupEditAction  extends AbstractAction{
    private static final long serialVersionUID = 383699559784475900L;

    private Group item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_GROUP_EDIT)) {
            return SUCCESS;
        }

        try {
            if(item==null) return SUCCESS;
            item.setUserCreated(user.getUsername());
            item.setUserUpdated(user.getUsername());
            beanFmsDao.editGroup(item);
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.utility.grant.GroupEditActon.excute: "+e.getMessage());
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
