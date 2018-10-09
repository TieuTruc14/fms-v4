package com.eposi.fms.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

public abstract class AbstractImageAction extends AbstractAction implements ServletRequestAware {
	private static final long serialVersionUID = 2530473373095830495L;
	
	@SuppressWarnings("unused")
	private HttpServletRequest servletRequest;

	public abstract byte[] getCustomImageInBytes();

	public abstract String getCustomContentType();
 
	public abstract String getCustomContentDisposition();
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
 
	}

}
