package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class DeviceInVehicleJsonAction extends AbstractAction{
    private static final long serialVersionUID = 375984164126582496L;
    private List<Device> devices;
    private String vehicleId;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_VIEW)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        try {
            if(!vehicleId.isEmpty()) {
                Vehicle vehicle = beanFmsDao.getVehicle(vehicleId);
                devices = beanFmsDao.searchDeviceByVehicle(vehicle);
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.vehicle.device.json" + e.getMessage());
        }
        return SUCCESS;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
