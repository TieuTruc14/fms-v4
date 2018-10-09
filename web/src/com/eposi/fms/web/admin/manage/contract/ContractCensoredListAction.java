package com.eposi.fms.web.admin.manage.contract;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 7/10/2016.
 */
public class ContractCensoredListAction extends AbstractAction {

    private static final long serialVersionUID = 8745832911157795478L;
    private PagingResult page = new PagingResult();
    private String vehicleId;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_TYPE_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        page = new PagingResult();
        try {
            if(page.getPageNumber()>0){
                page.setPageNumber(page.getPageNumber()-1);
            }
            if (user != null) {
                beanFmsDao.pageContractCensored(page,vehicleId);
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.contract.ContractCensoredListAction.execute: "+e.getMessage());
            return INPUT;
        }

        return SUCCESS;
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
