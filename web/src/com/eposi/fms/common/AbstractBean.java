package com.eposi.fms.common;

import java.io.Serializable;

import com.eposi.fms.model.Text;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractBean implements ApplicationContextAware, Serializable {
    private static final long serialVersionUID = 7555719288438179798L;
    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public Object getBean(String beanName) {
        return this.applicationContext.getBean(beanName);
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