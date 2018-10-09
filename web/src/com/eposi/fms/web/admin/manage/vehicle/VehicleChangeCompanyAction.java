package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 7/1/2016.
 */
public class VehicleChangeCompanyAction extends AbstractAction {
    private static final long serialVersionUID = -7458592773855103715L;

    private String id;
    private String vehicleId;
    private String companyDesCode;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(id==null || StringUtils.isEmpty(id) || vehicleId==null || StringUtils.isEmpty(vehicleId)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if ( StringUtils.isNotEmpty(companyDesCode)) {
            Vehicle vehicle=beanFmsDao.getVehicle(vehicleId);

            if(vehicle==null){
                addActionError("action.error.permission");
                return ERROR;
            }

            if(!beanFmsDao.isParent(vehicle.getCompany(), user.getCompany())){
                addActionError("action.error.permission");
                return ERROR;
            }

            Company company=null;
            if(StringUtils.isNotEmpty(companyDesCode)){
                company=beanFmsDao.getCompany(companyDesCode);
            }

            if(company!=null){
                if(!beanFmsDao.isParent(company, user.getCompany())){
                    addActionError("action.error.permission");
                    return ERROR;
                }

                try {
                    //update vehicleCount of two company
                    Company companySource=vehicle.getCompany();
                    companySource.setVehicleCount(companySource.getVehicleCount()-1);
                    company.setVehicleCount(company.getVehicleCount()+1);
                    beanFmsDao.editCompany(companySource);
                    beanFmsDao.editCompany(company);
                    //leave vehicle
                    beanFmsDao.changevehicleCompany(vehicle, company);

                    //leave device v2
                    beanFmsV2Dao.vehicleChangeCompany(vehicle, company);
                }catch (Exception e){
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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCompanyDesCode() {
        return companyDesCode;
    }

    public void setCompanyDesCode(String companyDesCode) {
        this.companyDesCode = companyDesCode;
    }
}
