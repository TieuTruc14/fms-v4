package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.PagingResult;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

public class CompanyListingAction extends AbstractAction {
    private static final long serialVersionUID = -872071484822391203L;

    private PagingResult page = new PagingResult();

    // filter parameters
    private String id; // filter by Id
    private String name; // filter by name
    private String owner;//filter by owner

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }
            Company company=user.getCompany();
//            if(user.getCompany().isTechnician()){
//                if(user.getCompany().getParent().getParent()!=null){
//                    company=user.getCompany().getParent();
//                }
//            }
            beanFmsDao.pageCompany(page, id, name, owner, null, company);
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.company.CompanyListingAction.execute: "+e.getMessage());
            return INPUT;
        }

        return SUCCESS;
	}

    public PagingResult getPage() {
        return page;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
