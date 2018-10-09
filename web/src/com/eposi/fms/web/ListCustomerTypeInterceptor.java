package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.CustomerType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TienManh on 4/4/2016.
 */
public class ListCustomerTypeInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = 4333088993479145131L;
    private static List<CustomerType> customerTypes = null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = ActionContext.getContext().getValueStack();

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        customerTypes = beanFmsDao.listCustomerType();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            //cần check quyền ở đây nữa
                stack.set("ListCustomerTypeInterceptor_customerTypes", customerTypes);
        }

        String result = invocation.invoke();

        return result;
    }

    public static List<CustomerType> getCustomerTypes() {
        return customerTypes;
    }


    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
