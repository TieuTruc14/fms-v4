package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


public class CompanyEditAction extends AbstractAction {
    private static final long serialVersionUID = 1788285538972807754L;
    private String id;
    private Company item;
    private List<District> districts=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_EDIT) || StringUtils.isEmpty(id)){
            addActionError("action.error.permission");
            return INPUT;
        }
        item  = beanFmsDao.getCompany(id);
        if(item!=null) {
            if(item.isInforLocked()){//neu cong ty da khoa infor
                if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_DELETE)){
                    addActionError("action.error.permission");
                    return ERROR;
                }
            }
            if (item.getAddress() != null) {
                districts = beanFmsDao.searchDistrictByProvince(item.getAddress().getProvince());
                communes = beanFmsDao.searchCommuneByDistrict(item.getAddress().getDistrict());
            }
        }

        return  SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company getItem() {
        return item;
    }

    public void setItem(Company item) {
        this.item = item;
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
}
