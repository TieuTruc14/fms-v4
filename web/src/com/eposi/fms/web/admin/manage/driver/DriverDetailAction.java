package com.eposi.fms.web.admin.manage.driver;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Driver;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class DriverDetailAction extends AbstractAction {
    private static final long serialVersionUID = 6403006214314860764L;

    private String driverId;
    private String companyId;
    private Driver item;
    private String strlicenseDay;
    private String strlicenseExp;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DRIVER_VIEW)) return SUCCESS;
		if (StringUtils.isNotEmpty(driverId)) {
			item = beanFmsDao.getDriver(driverId);
            if(item.getLicenceDay()!=null) {
                setStrlicenseDay(FormatUtil.formatDate(item.getLicenceDay(), "yyyy/MM/dd"));
            }
            if(item.getLicenceExp()!=null) {
                setStrlicenseExp(FormatUtil.formatDate(item.getLicenceExp(), "yyyy/MM/dd"));
            }
		}

		return SUCCESS;
	}

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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
