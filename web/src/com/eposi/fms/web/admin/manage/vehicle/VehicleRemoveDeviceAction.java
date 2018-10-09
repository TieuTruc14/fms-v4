package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TienManh on 6/13/2016.
 */
public class VehicleRemoveDeviceAction extends AbstractAction {

    private static final long serialVersionUID = 818226135422877590L;
    private String vehicleId;
    private String id;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user ==null){
            addActionError("action.error.user");
            return ERROR;
        }

        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_DEVICE_DELETE)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try{
            if(StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(vehicleId)){
               Device item= beanFmsDao.getDevice(id);
                Vehicle vehicle=beanFmsDao.getVehicle(vehicleId);
                if(item!=null){
                    item.setVehicle(null);
                    item.setUnit(0);
                    if(item.getDescription()!=null && item.getDescription().length()>0){
                        item.setDescription(item.getDescription() + "-Tháo khỏi biển " + vehicleId + "(" + FormatUtil.formatDate(new Date(), "dd/MM/yyyy") + ")");
                    }else{
                        item.setDescription(" Tháo khỏi biển " + vehicleId + "(" + FormatUtil.formatDate(new Date(), "dd/MM/yyyy") + ")");
                    }

                    beanFmsDao.editDevice(item);

                    //delete V2
                    int productType=item.getBatch().getModel().getProductType().getType();
                    if(productType==1 || productType==0) beanFmsV2Dao.deleteVehicle(vehicle);
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.vehicle.remove.devcie.execute: "+e.getMessage());
        }
        return SUCCESS;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
