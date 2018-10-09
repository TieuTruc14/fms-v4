package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.TransportType;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;

import java.util.List;

public class ListTransportTypeInterceptor extends AbstractAction implements Interceptor{
    private static final long serialVersionUID = 2111706927028964682L;

    private List<TransportType> transportTypes=null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = ActionContext.getContext().getValueStack();
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        transportTypes = beanFmsDao.listTransportType();

        stack.set("ListTansportTypesInterceptor_transportTypes", transportTypes);
        String result = invocation.invoke();

        return result;
    }

    public List<TransportType> getTransportTypes() {
        return transportTypes;
    }

    @Override
    public void destroy() {

    }
    @Override
    public void init() {

    }
}
