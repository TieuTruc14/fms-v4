package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class ListVehicleInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = 8008363739611626319L;

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ValueStack stack = ActionContext.getContext().getValueStack();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null) {
            vehicles = beanFmsDao.searchAllVehicleManagerByCompany(user.getCompany());
            stack.set("ListVehicleInterceptor_vehicles", vehicles);
        }
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
