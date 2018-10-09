package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 9/22/2016.
 */
public class CompanyUnlockInforAction extends AbstractAction {
    private static final long serialVersionUID = -4245210582402178856L;
    private String id;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_ORG_DELETE)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(id==null || StringUtils.isEmpty(id)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try{
            Company company=beanFmsDao.getCompany(id);
            if(company!=null){
                company.setInforLocked(false);
                beanFmsDao.editCompany(company);
            }
        }catch (Exception e){
            System.out.println("ERROR Here: Com.eposi.fms.web.admin.manage.Company.CompanyUnlockInforAction.execute");
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
