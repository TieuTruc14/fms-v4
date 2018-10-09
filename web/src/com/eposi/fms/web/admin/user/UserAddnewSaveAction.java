package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class UserAddnewSaveAction extends AbstractAction{
    private static final long serialVersionUID = -4380255109879161167L;

    private User   item;
    private String companyId;
    private String confirmPassword;
    private Company company;

    public String execute() throws Exception {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_USER_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if (StringUtils.isNotEmpty(companyId)){
            if(!confirmPassword.equals(item.getPassword()) || item.getPassword()==null || StringUtils.isEmpty(item.getPassword())){
                return INPUT;
            }

            Company company =beanFmsDao.getCompany(companyId);
            if(company!=null){
                if(item.getUsername()==null || StringUtils.isEmpty(companyId)){
                    return INPUT;
                }
                item.setCompany(company);
                item.setEnable(true);
                User exits=beanFmsDao.getUser(item.getUsername());
                if(exits!=null){
                    return INPUT;
                }
                //Create KonexyID for logging activity
                String description = "{ \"name\":\"" + item.getUsername() + "\"}";
                String konexyId = beanReportReader.newKonexyId(description);
                item.setKonexyId(konexyId);
                beanFmsDao.addUser(item);

                //add v2
                beanFmsV2Dao.addUser(item);

                // Activity
                Activity activity = new Activity();
                activity.setActionName("Addnew");
                activity.setActorName(user.getUsername());
                activity.setObjectId(item.getUsername());
                activity.setObjectName(User.class.getName());
                activity.setIndirectObjectName("/user/detail.action?id=" + item.getUsername());
                activity.setDate(new Date());
                activity.setContext("Thêm mới tài khoản");
                activity.setPassive(true);
                //Update log Company
                beanReportReader.saveActivity(item.getKonexyId(), activity);

            }
        }
        return "success";
    }


    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
