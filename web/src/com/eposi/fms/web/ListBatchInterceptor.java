package com.eposi.fms.web;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Batch;
import com.eposi.fms.persitence.FmsDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;

import java.util.ArrayList;
import java.util.List;

public class ListBatchInterceptor extends AbstractAction implements Interceptor {
    private static final long serialVersionUID = -6285040706451187462L;
    private List<Batch> batches= new ArrayList<>();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ValueStack stack = ActionContext.getContext().getValueStack();
        batches = beanFmsDao.listBatch();
        stack.set("ListBatchInterceptor_batches", batches);
        String result = invocation.invoke();
		return result;
	}


    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    public List<Batch> getBatchs() {
        return batches;
    }
}
