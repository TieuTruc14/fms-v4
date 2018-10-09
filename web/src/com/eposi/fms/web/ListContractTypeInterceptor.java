package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.ContractType;
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
public class ListContractTypeInterceptor extends AbstractAction implements Interceptor{
    private  List<ContractType> contractTypes=null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ValueStack stack = ActionContext.getContext().getValueStack();

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        contractTypes = beanFmsDao.listContractType();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            stack.set("ListContractTypeInterceptor_contractTypes", contractTypes);
        }
        String result = invocation.invoke();

        return result;
    }

    public  List<ContractType> getContractTypes() {
        return contractTypes;
    }



    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
