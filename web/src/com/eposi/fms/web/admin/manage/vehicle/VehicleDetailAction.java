package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class VehicleDetailAction extends AbstractAction {
    private static final long serialVersionUID = 9186725148822592095L;

    private String vehicleId;
    private Vehicle item;
    private List<Device> lstDevice=new ArrayList<>();
    private String messeage="";
    private String companyId;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_VIEW)){
            addActionError("action.error.permission");
            return INPUT;
        }
        try {
            if (StringUtils.isNotEmpty(vehicleId)){
                item = beanFmsDao.getVehicle(vehicleId);
                lstDevice=beanFmsDao.searchDeviceByVehicle(item, 1);

            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.vehicle.VehicleDetailAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public List<Device> getDeviceList(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        List<Device> devices= new ArrayList<Device>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null){
            Company company= item.getCompany();
            devices=beanFmsDao.searchDeviceCompanyToAddVehicle(company, 0);
        }
        return devices;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Vehicle getItem() {
        return item;
    }

    public List<Device> getLstDevice() {
        return lstDevice;
    }

    public void setLstDevice(List<Device> lstDevice) {
        this.lstDevice = lstDevice;
    }

    public String getMesseage() {
        return messeage;
    }

    public void setMesseage(String messeage) {
        this.messeage = messeage;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<Driver> getDrivers(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(companyId)) {
            Company company = beanFmsDao.getCompany(companyId);
            return beanFmsDao.searchDriverByCompany(company);
        }else{
            item = beanFmsDao.getVehicle(vehicleId);
            Company company=beanFmsDao.getCompany(item.getCompany().getId());
            return beanFmsDao.searchDriverByCompany(company);
        }

    }
}
