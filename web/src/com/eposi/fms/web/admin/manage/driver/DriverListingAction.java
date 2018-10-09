package com.eposi.fms.web.admin.manage.driver;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.PagingResult;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

public class DriverListingAction extends AbstractAction {
    private static final long serialVersionUID = 9044730081626947915L;

    private PagingResult page = new PagingResult();
    private String keyword;

	public String execute() {
        Company company = null;
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DRIVER_VIEW)) return SUCCESS;
        try {
            if (page.getPageNumber() > 0) {
                page.setPageNumber(page.getPageNumber() - 1);
            }

            if (user != null) {
                company = user.getCompany();
                beanFmsDao.pageDriver(page, keyword, company);
            }
        }catch(Exception e){
            System.out.println("com.eposi.fms.web.admin.manage.driver.DriverListingAction.execute:" + e.getMessage());
        }
        return SUCCESS;
	}

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
