package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 7/1/2016.
 */
public class ChangeMyPasswordAction extends AbstractAction {
    private User item;
    private String username;
    private String oldPass;
    private String newPassword;
    private String confirmPass;

    public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.getUsername().equals(username)) return SUCCESS;
        if(username==null|| StringUtils.isEmpty(username) || oldPass==null || StringUtils.isEmpty(oldPass)||newPassword==null || StringUtils.isEmpty(newPassword)||
                confirmPass==null || StringUtils.isEmpty(confirmPass) || !confirmPass.equals(newPassword)){
            return  SUCCESS;
        }
        item = beanFmsDao.getUser(username);
        if(item==null)return SUCCESS;

        if(item.getPassword().equals(oldPass)){
            item.setPassword(newPassword);
            beanFmsDao.editUser(item);
            //edit v2
            beanFmsV2Dao.editUser(item);

        }
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

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
