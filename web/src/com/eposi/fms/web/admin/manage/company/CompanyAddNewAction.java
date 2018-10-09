package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class CompanyAddNewAction extends AbstractAction {
    private static final long serialVersionUID = 5705652164867099919L;
    private String id;
    private String code;
    private Company item;
    private String companyLead;
    private Company companyLeader;
    private List<District> districts=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();
    private String companyParentId;
    private Company companyParent;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_ADD)){
            addActionError("action.error.permission");
            return INPUT;
        }
        if(companyLead!=null && StringUtils.isNotEmpty(companyLead)){
            companyLeader=beanFmsDao.getCompany(companyLead);
        }
        if(companyParentId!=null && StringUtils.isNotEmpty(companyParentId)){
            companyParent=beanFmsDao.getCompany(companyParentId);
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

    public Company getItem() {
        return item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyLead() {
        return companyLead;
    }

    public void setCompanyLead(String companyLead) {
        this.companyLead = companyLead;
    }

    public Company getCompanyLeader() {
        return companyLeader;
    }

    public void setCompanyLeader(Company companyLeader) {
        this.companyLeader = companyLeader;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public String getCompanyParentId() {
        return companyParentId;
    }

    public void setCompanyParentId(String companyParentId) {
        this.companyParentId = companyParentId;
    }

    public Company getCompanyParent() {
        return companyParent;
    }

    public void setCompanyParent(Company companyParent) {
        this.companyParent = companyParent;
    }
}
