package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Device;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListDeviceOfCompanyInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = -6285040706451187462L;
    private List<Device> devices= new ArrayList<>();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ValueStack stack = ActionContext.getContext().getValueStack();
        String companyId = (String) stack.findValue("companyId");
        if (!StringUtils.isEmpty(companyId)) {
            Company company = beanFmsDao.getCompany(companyId);
            if(company!=null) {
                devices = beanFmsDao.searchDeviceCompany(company, 0);
                stack.set("ListDeviceOfCompanyInterceptor_devices", devices);
            }
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

    public List<Device> getDevices() {
        return devices;
    }
}
