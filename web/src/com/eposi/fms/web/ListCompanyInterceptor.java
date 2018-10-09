package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by TieuTruc on 3/9/2015.
 */
public class ListCompanyInterceptor extends AbstractAction implements Interceptor {
    private  List<Company> companies= new LinkedList<>();

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ValueStack stack = ActionContext.getContext().getValueStack();
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if (user != null) {
            Company company = user.getCompany();
            if(company!=null) {
//                if (company.isTechnician()) {
//                    companies = beanFmsDao.getAllCompanyByParent(company.getParent());
//                    companies.add(company.getParent());
//                } else {
                    if (company.getParent()!=null) {
                        companies = beanFmsDao.getAllCompanyRelationShipByParent(company);
                    }else {
                        companies = beanFmsDao.getAllOrganizationByParent(company);
                    }
//                }

                for (Company item : companies) {
                    if (item.getId().equals(company.getId())) {
                        companies.remove(item);
                        break;
                    }
                }
            }

            stack.set("ListCompanyInterceptor_companies", companies);
        }

        String result = invocation.invoke();

        return result;
    }

    public List<Company> getCompanies() {
        return companies;
    }


    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
