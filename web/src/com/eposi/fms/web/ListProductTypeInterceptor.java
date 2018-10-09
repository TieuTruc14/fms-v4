package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.ProductType;
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
public class ListProductTypeInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = 4333088993479145131L;
    private static List<ProductType> productTypes = null;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = ActionContext.getContext().getValueStack();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        productTypes = beanFmsDao.getProductTypeByCompany(user.getCompany());
        if (productTypes != null && productTypes.size()>0) {
                stack.set("ListProductTypeInterceptor_models", productTypes);
        }

        String result = invocation.invoke();

        return result;
    }

    public static List<ProductType> getProductTypes() {
        return productTypes;
    }

    public static void setProductTypes(List<ProductType> productTypes) {
        ListProductTypeInterceptor.productTypes = productTypes;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
