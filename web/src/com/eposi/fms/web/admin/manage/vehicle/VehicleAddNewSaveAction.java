package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.RegexUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.model.Province;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.apache.commons.lang.StringUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

public class VehicleAddNewSaveAction extends AbstractAction {
    private static final long serialVersionUID = -5842694942367797585L;
    private Vehicle item;
    private String companyId;
    private Vehicle exstingItem;
    private Company company;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();


    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_ADD)){
            addActionError("action.error.permission");
            return ERROR;
        }

        if(companyId==null){
            return INPUT;
        }else{
            company=beanFmsDao.getCompany(companyId);
            if(company==null)return INPUT;
        }

        if (item == null || item.getId()==null || StringUtils.isEmpty(item.getId())|| item.getType().getId()==null || item.getCapacity()==null || item.getCapacity()==0 || item.getType().getId()==0){
            this.addActionError("Nhập đủ các trường vào!");
            return INPUT;
        }

        try {
            String vehicleId = item.getId();
            vehicleId = FormatUtil.removeSpecialCharacters(vehicleId.trim());
            vehicleId.toUpperCase();
            if(!RegexUtil.validateVehice(vehicleId)){
                return INPUT;
            }

            item.setId(vehicleId);
            exstingItem = beanFmsDao.getVehicle(item.getId());
            if (exstingItem != null) {
                this.addActionError("action.error.dupplicate");
                return INPUT;
            }

            if(item.getDriver().getId()==null || item.getDriver().getId().equals("")){
                item.setDriver(null);
            }
            Date now = new Date();
            item.setDateCreated(now);
            item.setUserCreated(user.getUsername());
            item.setVehicleNew(true);//xe vua tao, de tao hop dong

            //Create KonexyID for logging activity
            String description = "{ \"name\":\"" + item.getId() + "\"}";
            String konexyId = beanReportReader.newKonexyId(description);
            item.setKonexyId(konexyId);

            beanFmsDao.addVehicle(item);

            // get new Company Owner of this vehicle
            Company newCompany = beanFmsDao.getCompany(item.getCompany().getId());
            if (newCompany != null) {
                // update vehicle count for company
                newCompany.setVehicleCount(newCompany.getVehicleCount() + 1);
                beanFmsDao.editCompany(newCompany);

                // update vehicle for province
                Province province = newCompany.getProvince();
                if (province != null) {
                    province.setVehicleCount(province.getVehicleCount() + 1);
                    beanFmsDao.saveProvince(province);
                }
            }
            //add metadata report in konexy
            Report report = beanReportReader.newReportVehicleInKonexy(item.getId());
            beanFmsDao.addReport(report);
            // update vehicle Activity
            if(item.getKonexyId()!=null) {
                Activity activity = new Activity();
                activity.setId(company.getId());
                activity.setActionName("Addnew");
                activity.setActorName(user.getUsername());
                activity.setObjectId(item.getId());
                activity.setObjectName(Vehicle.class.getName());
                activity.setIndirectObjectName("/manage/vehicle/vehicle.detail.action?vehicleId=" + item.getId());
                activity.setDate(new Date());
                activity.setContext("Thêm mới phương tiện");
                activity.setPassive(true);
                //Save Vehicle Activity
                beanReportReader.saveActivity(item.getKonexyId(), activity);

                //Send message Activity to server Aggregate
                byte[] bytes = conf.asByteArray(activity);
                beanQueueingConsumerReportClient.put(bytes);
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }


    @Override
    public void validate() {
        super.validate();
    }

    public Vehicle getItem() {
        return item;
    }

    public Vehicle getExstingItem() {
        return exstingItem;
    }

    public List<Driver> getDrivers(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(companyId)) {
            Company company = beanFmsDao.getCompany(companyId);
            return beanFmsDao.searchDriverByCompany(company);
        }
        return null;
    }

    public void setItem(Vehicle item) {
        this.item = item;
    }

    public void setExstingItem(Vehicle exstingItem) {
        this.exstingItem = exstingItem;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
