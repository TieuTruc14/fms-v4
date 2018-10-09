package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Province;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class ListProvinceInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = 5034101752658696398L;

    private static List<Province> provinces = null;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ValueStack stack = ActionContext.getContext().getValueStack();

        if (provinces == null) {
            FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
            provinces = beanFmsDao.listProvince();
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            stack.set("ListProvinceInterceptor_provinces", provinces);
        }

		String result = invocation.invoke();

		return result;
	}

    public static List<Province> getProvinces() {
        return provinces;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
