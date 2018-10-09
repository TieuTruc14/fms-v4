package com.eposi.fms.web.admin.manage.driver;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Driver;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class DriverAddNewSaveAction extends AbstractAction {
    private static final long serialVersionUID = 341105522741634424L;

    private Driver item;
    private String companyId;
    private String strlicenseDay;
    private String strlicenseExp;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DRIVER_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if(item==null){
            return SUCCESS;
        }
        if(companyId==null||companyId.isEmpty()){
            return INPUT;
        }

        if(item.getId()==null || item.getId().equals("")){
            //gen key
            Driver driverKey=beanFmsDao.autoGenDriverKey();
            if(driverKey!=null){
                item.setId(driverKey.getId());
                item.setAutogen_key(driverKey.getAutogen_key());
            }else{
                this.addFieldError("item.id", "Không tạo được mã thẻ! Hãy thử lại.");
                return INPUT;
            }
        }


        Company company = beanFmsDao.getCompany(companyId);
        if(company==null){
           return INPUT;
        }
        item.setCompany(company);
        //Create KonexyID for logging activity
        String description = "{ \"name\":\"" + item.getId() + "\"}";
        String konexyId = beanReportReader.newKonexyId(description);
        item.setAggregation(konexyId);

        Date licenseDay = null;
        Date licenseExp = null;
        if (strlicenseDay == null || strlicenseDay.isEmpty() || strlicenseExp == null || strlicenseExp.isEmpty()) {
        }else{
            try {
                licenseDay     = FormatUtil.parseDate(strlicenseDay, "dd/MM/yyyy");
                licenseExp = FormatUtil.parseDate(strlicenseExp, "dd/MM/yyyy");
                item.setLicenceDay(licenseDay);
                item.setLicenceExp(licenseExp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {

            beanFmsDao.addDriver(item);
            //add v2
            beanFmsV2Dao.addDriverCard(item);
            addActionMessage("Thêm Lái xe thành công!!!");

            // Activity
            Activity activity = new Activity();
            activity.setId(company.getId());
            activity.setActionName("Addnew");
            activity.setActorName(user.getUsername());
            activity.setObjectId(item.getId());
            activity.setObjectName(Driver.class.getName());
            activity.setIndirectObjectName("/manage/driver/driver.detail.action?driverId="+item.getId());
            activity.setDate(new Date());
            activity.setContext("Thêm mới Lái xe:"+item.getName()+" số bằng " +item.getLicenceKey());
            activity.setPassive(true);
            activity.setIcon("<i class=\"fa fa-stop time-icon bg-dark\"></i>");

            //Send message Activity to server Aggregate
            byte[] bytes = conf.asByteArray(activity);
            beanQueueingConsumerReportClient.put(bytes);
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public Driver getItem() {
        return item;
    }

    public void setItem(Driver item) {
        this.item = item;
    }

    public String getStrlicenseDay() {
        return strlicenseDay;
    }

    public void setStrlicenseDay(String strlicenseDay) {
        this.strlicenseDay = strlicenseDay;
    }

    public String getStrlicenseExp() {
        return strlicenseExp;
    }

    public void setStrlicenseExp(String strlicenseExp) {
        this.strlicenseExp = strlicenseExp;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
