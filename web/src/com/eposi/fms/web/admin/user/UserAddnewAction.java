package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;


public class UserAddnewAction extends AbstractAction{
    private static final long serialVersionUID = 1053072546317554169L;
    private User   item;
    private String companyId;
    private Company company;

    public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");

        if (StringUtils.isNotEmpty(companyId)) {
           company=beanFmsDao.getCompany(companyId);
        }

        return "success";
    }


    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
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
