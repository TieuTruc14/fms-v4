package com.eposi.fms.web.admin.report;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Province;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class NationAction extends AbstractAction {
    private static final long serialVersionUID = -6933185111927426406L;

    private PagingResult page = new PagingResult();
    private String id; // filter by Id
    private String name; // filter by name

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_REPORT_NATION)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }

            beanFmsDao.pageCompanyAll(page, id, name,null, null,user.getCompany());
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.report.nation.execute: "+e.getMessage());
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

}
