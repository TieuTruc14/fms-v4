package com.eposi.fms.web.admin.manage.driver;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Driver;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.model.DriverCard;
import com.eposi.fms.v2.model.Owner;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 9/15/2016.
 */
public class DriverChangeCompanyAction  extends AbstractAction {
    private static final long serialVersionUID = -2687845136466668809L;
    private String id;
    private String driverId;
    private String companyDesCodeDriver;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DRIVER_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(id==null || StringUtils.isEmpty(id) || driverId==null || StringUtils.isEmpty(driverId)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if (StringUtils.isNotEmpty(companyDesCodeDriver)) {
            Driver driver=beanFmsDao.getDriver(driverId);

            if(driver==null){
                addActionError("action.error.permission");
                return ERROR;
            }

            if(!beanFmsDao.isParent(driver.getCompany(), user.getCompany())){
                addActionError("action.error.permission");
                return ERROR;
            }

            Company company=null;
            if(StringUtils.isNotEmpty(companyDesCodeDriver)){
                company=beanFmsDao.getCompany(companyDesCodeDriver);
            }

            if(company!=null){
                if(!beanFmsDao.isParent(company, user.getCompany())){
                    addActionError("action.error.permission");
                    return ERROR;
                }

                try {
                    //update vehicleCount of two company
                    Company companySource=driver.getCompany();
                    companySource.setDriverCount(companySource.getDriverCount() - 1);
                    company.setDriverCount(company.getDriverCount() + 1);
                    beanFmsDao.editCompany(companySource);
                    beanFmsDao.editCompany(company);
                    //leave driver
                    driver.setCompany(company);
                    beanFmsDao.editDriver(driver);

                    //leave driver_card v2
                    DriverCard driverCard=beanFmsV2Dao.getDriver(driverId);
                    Owner owner=new Owner();
                    owner.setId(company.getOwner());
                    driverCard.setOwner(owner);
                    driverCard.setDefaultVehicle("");
                    beanFmsV2Dao.editDriver(driverCard);
                }catch (Exception e){
                    System.out.println("Error when change company for driver: Fms.web.admin.manage.driver.DriverChangeCompanyAction.execute");
                    e.printStackTrace();
                }

            }

        }

        return SUCCESS;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCompanyDesCodeDriver() {
        return companyDesCodeDriver;
    }

    public void setCompanyDesCodeDriver(String companyDesCodeDriver) {
        this.companyDesCodeDriver = companyDesCodeDriver;
    }
}
