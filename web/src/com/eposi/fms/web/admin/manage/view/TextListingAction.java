package com.eposi.fms.web.admin.manage.view;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Text;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class TextListingAction extends AbstractAction {
    private static final long serialVersionUID = 5904220747367364767L;

    private List<Text> items = new ArrayList<Text>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_TEXT_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        items = beanFmsDao.listText();

        return SUCCESS;
    }

    public List<Text> getItems() {
        return items;
    }
}
