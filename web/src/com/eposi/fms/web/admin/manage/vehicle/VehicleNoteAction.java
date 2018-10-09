package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class VehicleNoteAction extends AbstractAction {
    private static final long serialVersionUID = 2813335575028465961L;
    private Vehicle item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");

        if(item == null){
            addActionError("action.error.device.null");
            return ERROR;
        }



        if(item!=null) {
            Vehicle vehicle = beanFmsDao.getVehicle(item.getId());
            if(!beanFmsDao.isParent(vehicle.getCompany(), user.getCompany())){
                addActionError("action.error.permission");
                return ERROR;
            }

            if(vehicle.getKonexyId()!=null) {
                Activity activity = new Activity();
                activity.setIndirectObjectName("/manage/vehicle/vehicle.detail.action?vehicleId=" + item.getId());
                activity.setActionName("Note");
                activity.setActorName(user.getUsername());
                activity.setObjectId(item.getId());
                activity.setObjectName(Vehicle.class.getName());
                activity.setContext(item.getNote());
                activity.setDate(new Date());
                activity.setPassive(true);
                beanReportReader.saveActivity(vehicle.getKonexyId(), activity);
            }
        }
        return SUCCESS;
	}

    public Vehicle getItem() {
        return item;
    }

    public void setItem(Vehicle item) {
        this.item = item;
    }
}
