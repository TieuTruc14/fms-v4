package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Driver;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import org.apache.axis.utils.StringUtils;

import java.util.List;

public class VehicleAddNewAction extends AbstractAction {
    private static final long serialVersionUID = 3279638862642772863L;

    private String companyId; // helper
    private Company company;
    private Vehicle item;
    private String driverId;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");

        if (!StringUtils.isEmpty(companyId)){
            company = beanFmsDao.getCompany(companyId);
        }

        return SUCCESS;
    }

    public List<Driver> getDrivers(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(companyId)) {
            Company company = beanFmsDao.getCompany(companyId);
           return beanFmsDao.searchDriverByCompany(company);
        }
        return null;
    }

    public Company getCompany() {
        return company;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Vehicle getItem() {
        return item;
    }

    public void setItem(Vehicle item) {
        this.item = item;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
