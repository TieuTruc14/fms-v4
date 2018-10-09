package com.eposi.fms.web.admin.manage.organization;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;


public class OrgazationListingAction extends AbstractAction {
    private static final long serialVersionUID = -5909923466123444388L;

    private List<Company> items;
    private String id; // filter by Id
    private String name; // filter by name

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_ORG_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try {

            Company parent = user.getCompany();
            if(parent!=null) {
                 items = beanFmsDao.searchOrganizationByParentAndIdName(parent, id, name);
                if((id==null)&&(name==null)){
                    items.add(parent);
                }

            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.company.OrgazationListingAction.execute: "+e.getMessage());
            return INPUT;
        }

        return SUCCESS;
	}

    public List<Company> getItems() {
        return items;
    }

    public void setItems(List<Company> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
