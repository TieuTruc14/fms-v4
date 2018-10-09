package com.eposi.fms.web;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang.StringUtils;

public class RequestParametersNormalizationInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = 6104173828338332903L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");

        ValueStack stack = ActionContext.getContext().getValueStack();
        String vehicleId = (String) stack.findValue("vehicleId");
        String companyId = (String) stack.findValue("companyId");
        String provinceId = (String) stack.findValue("provinceId");

        if (StringUtils.isNotEmpty(vehicleId) && StringUtils.isEmpty(companyId) && StringUtils.isEmpty(provinceId)) {
            vehicleId = FormatUtil.removeSpecialCharacters(vehicleId);
            Vehicle vehicle = beanFmsDao.getVehicle(vehicleId);

            // put the companyId and provinceId to the stack
            if (vehicle != null) {
                try {
                    companyId = vehicle.getCompany().getId();
                    provinceId = vehicle.getCompany().getProvince().getId();

                    stack.set("companyId", companyId);
                    stack.set("provinceId", provinceId);
                } catch(Exception e) {

                }
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
}
