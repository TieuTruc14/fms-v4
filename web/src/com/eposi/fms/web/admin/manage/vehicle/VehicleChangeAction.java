package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class VehicleChangeAction extends AbstractAction {
    private static final long serialVersionUID = 3368148157026736704L;

    private String vehicleId;
    private Vehicle item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        try {
            if (StringUtils.isNotEmpty(vehicleId)){
                item = beanFmsDao.getVehicle(vehicleId);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.vehicle.VehicleChangeAction: "+ e.getMessage());
        }

        return SUCCESS;
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

}
