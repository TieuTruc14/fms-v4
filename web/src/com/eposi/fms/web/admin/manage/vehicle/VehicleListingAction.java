package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

public class VehicleListingAction extends AbstractAction {
    private static final long serialVersionUID = -3901656365557307953L;

    private String companyId;
    private String vehicleId; // filter by vehicle
    private PagingResult page = new PagingResult();

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        Company company = user.getCompany();
        if(companyId!=null){
            company = beanFmsDao.getCompany(companyId);
            if(company!=null) {
                if (!beanFmsDao.isParent(company, user.getCompany())) {
                    company = user.getCompany();
                }
            }
        }

        if(user==null || user.getCompany()==null) return SUCCESS;
        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }
            beanFmsDao.pageVehicle(page, vehicleId, company);
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.vehicle.VehicleListingAction.excute: "+e.getMessage());
        }
        return SUCCESS;
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

}
