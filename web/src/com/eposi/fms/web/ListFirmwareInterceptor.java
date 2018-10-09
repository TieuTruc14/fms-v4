package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Firmware;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by TienManh on 5/21/2016.
 */
public class ListFirmwareInterceptor extends AbstractAction implements Interceptor{

    private static final long serialVersionUID = -1838495782950623753L;
    private List<Firmware> firmwares=null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ValueStack stack = ActionContext.getContext().getValueStack();

        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        firmwares = beanFmsDao.listFirmware();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            stack.set("ListFirmwaresInterceptor_firmwares", firmwares);
        }
        String result = invocation.invoke();

        return result;
    }

    public List<Firmware> getFirmwares() {
        return firmwares;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
