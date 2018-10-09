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

import java.util.List;


public class ListOrganizationByUserInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = -9121682023181945162L;

    private List<Company> organizationList= null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ValueStack stack = ActionContext.getContext().getValueStack();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
            Company item =user.getCompany();
            organizationList = beanFmsDao.getAllOrganizationByParent(item);
            stack.set("ListOrganizationByUserInterceptor_orgazations", organizationList);
        }
        String result = invocation.invoke();

        return result;
    }

    public List<Company> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Company> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
