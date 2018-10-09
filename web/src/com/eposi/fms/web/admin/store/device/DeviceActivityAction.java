package com.eposi.fms.web.admin.store.device;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

public class DeviceActivityAction extends AbstractAction {
    private static final long serialVersionUID = -2223001324696723926L;

    private String  deviceId;
    private Device item;
    private Vehicle vehicle;
    private String start; // start date
    private String end; // end date
//    private VehicleState   vehicleState;
    private List<Activity> activitys;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        Date dateStart = null;
        Date dateEnd   = null;

        // date
        if (org.apache.commons.lang3.StringUtils.isEmpty(start)) {
            start = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(end)) {
            end = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }

        try {
            dateStart = FormatUtil.parseDate(start, "dd/MM/yyyy");
        } catch(Exception e)  {
        }
        try {
            dateEnd = FormatUtil.parseDate(end, "dd/MM/yyyy");
        } catch(Exception e)  {
        }
        if (StringUtils.isEmpty(deviceId)){
            return SUCCESS;
        }

        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");

        item = beanFmsDao.getDevice(deviceId);
        vehicle = item.getVehicle();
        if(vehicle!=null) {
//            HazelcastClientMapAkka beanHazelcastClientMapAkka = (HazelcastClientMapAkka) this.getBean("beanHazelcastClientMapAkka");
//            vehicleState = beanHazelcastClientMapAkka.get(deviceId);
//            setVehicleState(vehicleState);
        }

        try {
            if(item.getKonexyId()!=null) {
                activitys = beanReportReader.searchActivity(item.getKonexyId(), dateStart, dateEnd);
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.device.DeviceActivityAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

//    public VehicleState getVehicleState() {
//        return vehicleState;
//    }
//
//    public void setVehicleState(VehicleState vehicleState) {
//        this.vehicleState = vehicleState;
//    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Device getItem() {
        return item;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<Activity> activitys) {
        this.activitys = activitys;
    }
}
