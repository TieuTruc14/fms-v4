package com.eposi.fms.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.eposi.fms.model.Text;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.DefaultActionSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author tuanpa
 *
 */
public abstract class AbstractAction extends DefaultActionSupport implements ApplicationContextAware {
	private static final long serialVersionUID = -1656754891826769705L;
	
	protected static final String POST = "POST";
	protected static final String GET = "GET";
	
	protected ApplicationContext applicationContext;
	
	protected ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	protected Validator validator = factory.getValidator();	

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public Object getBean(String strBeanName) {
		return this.applicationContext.getBean(strBeanName);
	}

	public boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

    public  String getTitleText(String key){
        FmsDao beanFmsDao =(FmsDao)getBean("beanFmsDao");
        Text text = beanFmsDao.getText(key);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name = "";
        switch (user.getView()){
            case 0:
                name = text.getNameVN();
                break;
            case 1:
                name = text.getNameE();
                break;
            case 2:
                name = text.getNameTaxi();
                break;
            case 3:
                name = text.getNameBike();
                break;
            case 4:
                name = text.getNameShip();
                break;
            case 5:
                name = text.getNameFerry();
                break;
            default:
                name = text.getNameVN();
                break;
        }

        return name;
    }
}