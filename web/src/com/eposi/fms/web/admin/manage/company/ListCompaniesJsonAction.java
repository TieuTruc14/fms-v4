package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Province;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class ListCompaniesJsonAction extends AbstractAction {
    private static final long serialVersionUID = -7918876184359470278L;

    private String provinceId;
    //
    private List<Company> companies = new ArrayList<Company>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }

        if (StringUtils.isNotEmpty(provinceId)) {
            Province province = beanFmsDao.getProvince(provinceId);
            if (province != null) {
                companies = beanFmsDao.searchCompanyByProvince(province);

                for (Company company : companies) {
                    company.setId(company.getId());
                    company.setName(company.getName().trim());
                }
            }
        }

        return SUCCESS;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
