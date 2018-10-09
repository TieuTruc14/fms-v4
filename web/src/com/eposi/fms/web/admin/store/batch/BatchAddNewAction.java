package com.eposi.fms.web.admin.store.batch;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Batch;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;


public class BatchAddNewAction extends AbstractAction {

    private static final long serialVersionUID = 7511573663313179041L;
    private Batch item;
    private String strDateStart;
    private String strDateEnd;
    private String modelId;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_ADD)){
            addActionError("action.error.permission");
            return INPUT;
        }
        return SUCCESS;
    }

    public Batch getItem() {
        return item;
    }

    public void setItem(Batch item) {
        this.item = item;
    }

    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public String getStrDateEnd() {
        return strDateEnd;
    }

    public void setStrDateEnd(String strDateEnd) {
        this.strDateEnd = strDateEnd;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

}
