package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

public class StockInventoryDetailAction extends AbstractAction {
    private static final long serialVersionUID = -6182619450194788707L;

    private PagingResult page = new PagingResult();
    private String  id;
    private String  companyId;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_IN_VIEW)){
                addActionError("action.error.permission");
                return SUCCESS;
            }

            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() - 1);
            }
            if(companyId!=null) {
                if(!companyId.isEmpty()) {
                    Company company = beanFmsDao.getCompany(companyId);
                    if(company!=null) {
                        beanFmsDao.pageDeviceInventory(page, id, company);
                    }
                }
            }

        } catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.VehicleListingAction.excute: "+e.getMessage());
        }
        return SUCCESS;
	}

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
