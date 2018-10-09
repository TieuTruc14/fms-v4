package com.eposi.fms.web.catalog.model;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Model;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class CatalogModelDetailAction extends AbstractAction {
    private static final long serialVersionUID = 278764420622126480L;

    private String id;
    private Model item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_MODEL_VIEW)) return SUCCESS;
        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getModel(id);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.catalog.InsuranceDetailAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Model getItem() {
        return item;
    }

    public void setItem(Model item) {
        this.item = item;
    }
}
