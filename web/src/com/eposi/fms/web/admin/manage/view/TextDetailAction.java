package com.eposi.fms.web.admin.manage.view;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Text;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class TextDetailAction extends AbstractAction {
    private static final long serialVersionUID = -5388563377554325287L;

    private String id;
    private Text item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_TEXT_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getText(id);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.manage.view.TextDetailAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Text getItem() {
        return item;
    }

    public void setItem(Text item) {
        this.item = item;
    }
}
