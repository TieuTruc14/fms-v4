package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class ChangePasswordAction extends AbstractAction{
    private static final long serialVersionUID = -1225877086954941872L;

    private User item;
    private String username;
    private String newPassword;

    public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_USER_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(username==null|| StringUtils.isEmpty(username)){
            return  SUCCESS;
        }

        item = beanFmsDao.getUser(username);

        if(item==null){
            return SUCCESS;
        }
        if(newPassword==null|| StringUtils.isEmpty(newPassword)){
            return  SUCCESS;
        }

        item.setPassword(newPassword);
        beanFmsDao.editUser(item);
        //edit v2
        beanFmsV2Dao.editUser(item);

        addActionMessage("Cập nhật mật khẩu mới thành công vào hệ thống !");

        return SUCCESS;
    }

    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
