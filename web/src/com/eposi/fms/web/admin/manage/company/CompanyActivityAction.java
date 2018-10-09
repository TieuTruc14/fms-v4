package com.eposi.fms.web.admin.manage.company;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompanyActivityAction extends AbstractAction {
    private static final long serialVersionUID = 3456296574439127727L;

    private String companyId;
    private Company item;
    private String start; // start date
    private String end; // end date
    private List<Activity> activitys; // for displaying recent activities in detail page

	public String execute() {

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }

        Date dateStart = null;
        Date dateEnd   = new Date();
        // date
        if (org.apache.commons.lang3.StringUtils.isEmpty(start)) {
            start = "01/06/2016";
        }
        try {
            dateStart = FormatUtil.parseDate(start, "dd/MM/yyyy");
        } catch(Exception e)  {
        }

        try {
            if (StringUtils.isNotEmpty(companyId)){
                item = beanFmsDao.getCompany(companyId);
                ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
                activitys = beanReportReader.searchActivity(item.getKonexyId(), dateStart, dateEnd);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.vehicle.VehicleActivityAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public List<Vehicle> getVehicles() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");

        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        if (item != null) {
            vehicles = beanFmsDao.searchVehicleByCompany(item);
        }

        return vehicles;
    }


    public List<Driver> getDrivers() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        List<Driver> drivers = new ArrayList<Driver>();
        drivers = (List<Driver>) beanFmsDao.searchDriverByCompany(item);

        return drivers;
    }

    public List<User> getUsers() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        List<User> users = new ArrayList<User>();
        users  = (List<User>) beanFmsDao.searchUserByCompany(item);

        return users;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Company getItem() {
        return item;
    }

    public void setItem(Company item) {
        this.item = item;
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

    public List<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<Activity> activitys) {
        this.activitys = activitys;
    }
}
