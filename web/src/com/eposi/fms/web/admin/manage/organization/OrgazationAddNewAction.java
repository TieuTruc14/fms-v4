package com.eposi.fms.web.admin.manage.organization;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Commune;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.District;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class OrgazationAddNewAction extends AbstractAction {
    private static final long serialVersionUID = 5705652164867099919L;
    private String id;
    private String code;
    private Company item;
    private List<District> districts=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();
    private String companyParentId;
    private Company companyParent;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_ORG_ADD)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(companyParentId!=null && StringUtils.isNotEmpty(companyParentId)){
            companyParent=beanFmsDao.getCompany(companyParentId);
        }
		return SUCCESS;
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
