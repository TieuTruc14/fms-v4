package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


public class StockRecoveryDeviceAction extends AbstractAction {
    private static final long serialVersionUID = -1290064326998581705L;

    private String companyId;
    private Company company;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(companyId!=null) {
            company = beanFmsDao.getCompany(companyId);
            if(company!=null){
                if(!beanFmsDao.isParent(company, user.getCompany())){
                    addActionError("action.error.permission");
                    return ERROR;
                }
            }
        }

        return SUCCESS;
    }

    public List<Device> getDeviceList(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Device> devices= new ArrayList<Device>();
        if(companyId!=null) {
            Company company = beanFmsDao.getCompany(companyId);
            if(company!=null) {
                if (!company.getId().equals(user.getCompany().getId())) {
                    devices = beanFmsDao.searchDeviceCompanyToRecover(company, 0, false);
                }
            }
        }

        return devices;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
