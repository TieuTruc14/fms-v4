package com.eposi.fms.common.web;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class ImageBytesResult  implements Result {
	private static final long serialVersionUID = -7989300672738215353L;

	public void execute(ActionInvocation invocation) throws Exception {
		AbstractImageAction action = (AbstractImageAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
 
		response.setContentType(action.getCustomContentType());
		response.getOutputStream().write(action.getCustomImageInBytes());
		response.getOutputStream().flush();
	}
	
}
