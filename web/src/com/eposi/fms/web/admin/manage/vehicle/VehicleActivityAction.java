package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.apache.commons.lang.StringUtils;


import java.util.Date;
import java.util.List;

public class VehicleActivityAction extends AbstractAction {
    private static final long serialVersionUID = 9186725148822592095L;

    private String vehicleId;
    private Vehicle item;
    private String start; // start date
    private String end; // end date
    private List<Activity> activitys; // for displaying recent activities in detail page
    private List<Device> devices;

	public String execute() {
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

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        try {
            if (StringUtils.isNotEmpty(vehicleId)){
                item = beanFmsDao.getVehicle(vehicleId);
                if(item!=null) {
                    activitys = beanReportReader.searchActivity(item.getKonexyId(), dateStart, dateEnd);
                    devices = beanFmsDao.searchDeviceByVehicle(item);
                }
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.vehicle.VehicleActivityAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

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

    public Vehicle getItem() {
        return item;
    }

    public List<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<Activity> activitys) {
        this.activitys = activitys;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
