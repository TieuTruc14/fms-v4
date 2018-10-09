package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.persitence.FmsDao;

public class UserRemoveAuthenAction extends AbstractAction {
    private static final long serialVersionUID = 1179907217150470379L;

    private String username;
    private String authority;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
