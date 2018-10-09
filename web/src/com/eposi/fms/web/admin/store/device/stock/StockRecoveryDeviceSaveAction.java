package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class StockRecoveryDeviceSaveAction extends AbstractAction {
    private static final long serialVersionUID = 3912165425301605992L;

    private List<DeviceItem> devices;
    private String companyId;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try{
            List<Device> deviceList= new ArrayList<Device>();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!(beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_ADD) || beanFmsDao.isPermision(user, PermissionDefine.ROLE_ORG_ADD))){
                addActionError("action.error.permission");
                return ERROR;
            }

            if(devices!=null  && user!=null){
                Company company=user.getCompany();
                for ( DeviceItem item : devices) {
                    if (StringUtils.equals("on", item.getCheckbox())) {
                        Device device=beanFmsDao.getDevice(item.getId());
                        device.setCompany(company);
                        deviceList.add(device);
                    }
                }
                if(deviceList.size()>0){
                    beanFmsDao.saveOrUpdateDevices(deviceList);
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.addmin.device.stock.recover.save.execute: "+e.getMessage());
        }

        return SUCCESS;
    }

    public List<DeviceItem> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceItem> devices) {
        this.devices = devices;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public static class DeviceItem{
        private String id;
        private String checkbox;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCheckbox() {
            return checkbox;
        }

        public void setCheckbox(String checkbox) {
            this.checkbox = checkbox;
        }
    }
}
