package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserEditAction extends AbstractAction{
    private User item;

    public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_USER_EDIT)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if(item==null){
            return SUCCESS;
        }
        User oldItem = beanFmsDao.getUser(item.getUsername());
        if(oldItem!=null ) {
            oldItem.setName(item.getName());
            oldItem.setPhone(item.getPhone());
            oldItem.setView(item.getView());
            oldItem.setSupperUser(item.isSupperUser());
            beanFmsDao.editUser(oldItem);

            //edit v2
            beanFmsV2Dao.editUser(oldItem);
        }

        return INPUT;
    }

    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
    }
}
