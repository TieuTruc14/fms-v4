package com.eposi.fms.web.admin.manage.contract;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.persitence.PagingResult;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TieuTruc on 3/6/2015.
 */
public class ContractListingAction extends AbstractAction {

    private PagingResult page = new PagingResult();
    private String vehicleId;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_VIEW)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
          page = new PagingResult();
        try {
            if(page.getPageNumber()>0){
                page.setPageNumber(page.getPageNumber()-1);
            }
            if (user != null) {
                Company company =user.getCompany();
                beanFmsDao.pageContract(page, company, vehicleId);

            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.contract.ContractListingAction.execute: "+e.getMessage());
            return INPUT;
        }

        return SUCCESS;
    }

    public PagingResult getPage() {
        return page;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

}
