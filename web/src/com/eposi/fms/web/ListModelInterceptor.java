package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Model;
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
public class ListModelInterceptor extends AbstractAction implements Interceptor {

    private static final long serialVersionUID = -2464171230699550077L;
    private static List<Model> models = null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = ActionContext.getContext().getValueStack();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        models = beanFmsDao.getModelByCompany(user.getCompany());
        if (models != null && models.size()>0) {
            stack.set("ListModelInterceptor_models", models);
        }

        String result = invocation.invoke();

        return result;
    }

    public static List<Model> getModels() {
        return models;
    }

    public static void setModels(List<Model> models) {
        ListModelInterceptor.models = models;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
