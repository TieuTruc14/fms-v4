package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Driver;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class ListDriverOfCompanyInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = 6890165072696312658L;

    private List<Driver> drivers;
    private User user;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ValueStack stack = ActionContext.getContext().getValueStack();
        String companyId = (String) stack.findValue("companyId");
        if (StringUtils.isNotEmpty(companyId)) {
            Company company = beanFmsDao.getCompany(companyId);
            drivers = beanFmsDao.searchDriverByCompany(company);
        }else{
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user!=null) {
                drivers = beanFmsDao.searchDriverByCompany(user.getCompany());
            }
        }
        stack.set("ListDriverOfCompanyInterceptor_drivers", drivers);
        String result = invocation.invoke();

		return result;
	}

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
