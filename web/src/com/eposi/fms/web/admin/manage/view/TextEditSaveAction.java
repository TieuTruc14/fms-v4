package com.eposi.fms.web.admin.manage.view;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Text;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

public class TextEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = -3297764293380195747L;

    private Text item;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_TEXT_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if (item == null) {
            return SUCCESS;
        }
        if (item.getId() == null) {
            return SUCCESS;
        }

        try {
            beanFmsDao.editText(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        super.validate();
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public Text getItem() {
        return item;
    }

    public void setItem(Text item) {
        this.item = item;
    }
}
