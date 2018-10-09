package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TienManh on 7/12/2016.
 */
public class CompanyCheckPhoneJsonAction extends AbstractAction {

    private static final long serialVersionUID = 3231517221987419146L;
    private List<Company> companies;
    private String phone;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        try {
            if(!phone.isEmpty()) {
                companies = beanFmsDao.searchCompanyByPhone(phone);
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.company.check.phone.json" + e.getMessage());
        }

        return SUCCESS;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
