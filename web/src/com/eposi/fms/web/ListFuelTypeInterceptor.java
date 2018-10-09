package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FuelType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TieuTruc on 3/9/2015.
 */
public class ListFuelTypeInterceptor extends AbstractAction implements Interceptor{
    private static final long serialVersionUID = 4588145269218202699L;

    private  List<FuelType> fuelTypes=null;
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ValueStack stack = ActionContext.getContext().getValueStack();

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        fuelTypes = beanFmsDao.listFuelType();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            stack.set("ListFuelTypesInterceptor_fuelTypes", fuelTypes);
        }
        String result = invocation.invoke();

        return result;
    }

    public  List<FuelType> getFuelTypes() {
        return fuelTypes;
    }


    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
