package com.eposi.fms.web.admin.manage.driver;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Driver;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class DriverEditAction extends AbstractAction {
    private static final long serialVersionUID = 1010463588975688810L;

    private Driver item;
    private String companyId;
    private String strlicenseDay;
    private String strlicenseExp;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DRIVER_EDIT)){
            addActionError("action.error.permission");
            return SUCCESS;
        }

        if (strlicenseDay == null || strlicenseDay.isEmpty() || strlicenseExp == null || strlicenseExp.isEmpty()) {
            return SUCCESS;
        }
        Date licenseDay = null;
        Date licenseExp = null;
        try {
            licenseDay     = FormatUtil.parseDate(strlicenseDay, "yyyy/MM/dd");
            licenseExp = FormatUtil.parseDate(strlicenseExp ,"yyyy/MM/dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user==null){
            return INPUT;
        }


        try {
            if (item != null) {
                Driver itemOld=beanFmsDao.getDriver(item.getId());
                if(itemOld==null){
                    return INPUT;
                }
                itemOld.setName(item.getName());
                itemOld.setPhone(item.getPhone());
                itemOld.setLicenceDay(licenseDay);
                itemOld.setLicenceExp(licenseExp);
                itemOld.setLicenceKey(item.getLicenceKey());

                beanFmsDao.editDriver(itemOld);

                //edit v2
                beanFmsV2Dao.editDriverCard(itemOld);
                addActionMessage("Cập nhật phương tiện thành công!!!");

                // Activity
                Activity activity = new Activity();
                activity.setActionName("Update");
                activity.setActorName(user.getUsername());
                activity.setObjectId(item.getId());
                activity.setObjectName(Driver.class.getName());
                activity.setIndirectObjectName(null);
                activity.setDate(new Date());
                activity.setContext("Cập nhật Lái xe:"+item.getName()+" số bằng " +item.getLicenceKey());
                activity.setPassive(true);
                activity.setIcon("<i class=\"fa fa-stop time-icon bg-dark\"></i>");

                //Update log Company
                beanReportReader.saveActivity(item.getCompany().getKonexyId(), activity);

            } else {
                return INPUT;
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }

        return SUCCESS;
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

    public Driver getItem() {
        return item;
    }

    public void setItem(Driver item) {
        this.item = item;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
