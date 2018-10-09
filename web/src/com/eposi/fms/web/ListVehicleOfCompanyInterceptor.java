package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.model.Vehicle;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class ListVehicleOfCompanyInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = -2020442178866494998L;

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ValueStack stack = ActionContext.getContext().getValueStack();
        String companyId = (String) stack.findValue("companyId");
        if (StringUtils.isNotEmpty(companyId)) {
            vehicles = beanFmsDao.searchVehicleByCompanyId(companyId);
        }else{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user!=null) {
                vehicles = beanFmsDao.searchVehicleByCompanyId(user.getCompany().getId());
                for (Vehicle vehicle : vehicles) {
                    vehicle.setId(vehicle.getId().toUpperCase());
                }
            }
        }
        stack.set("ListVehicleOfCompanyInterceptor_vehicles", vehicles);
        String result = invocation.invoke();

		return result;
//        return SUCCESS;
	}

    private boolean checkContainsId(String vehicleId) {
        try {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getId().equals(vehicleId)) {
                    return true;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
