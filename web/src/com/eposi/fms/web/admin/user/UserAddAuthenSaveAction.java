package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.GroupMember;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TieuTruc on 6/4/2015.
 */
/*Class H-Code authority*/
public class UserAddAuthenSaveAction extends AbstractAction {

    private static final long serialVersionUID = 4502918510745702775L;
    private User item;
    private String username;
    private String companyId;
    private List<String> lstChoose=new ArrayList<>();
    private List<String> lstAllAuthor=new ArrayList<>();


    public String execute() {
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null){
            List<GroupMember> listAuthorSave=new ArrayList<GroupMember>();
            if(!beanFmsDao.isPermision(user,PermissionDefine.ROLE_USER_EDIT)){
                addActionError("action.error.permission");
                return ERROR;
            }
            Company company=beanFmsDao.getCompany(companyId);
            if(company==null) return ERROR;
            if(!beanFmsDao.isParent(company, user.getCompany())) {
                addActionError("action.error.permission");
                return ERROR;
            }

            if(lstChoose.size()>0){
                for(String group:lstChoose){
                    listAuthorSave.add(new GroupMember(group, item.getUsername()));
                }
            }
            beanFmsDao.deleteGroupMemberByUsername(item.getUsername());
            if(listAuthorSave.size()>0){
                beanFmsDao.saveOrUpdateGroupMembers(listAuthorSave);
            }
        }
        return  SUCCESS;
    }

    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<String> getLstChoose() {
        return lstChoose;
    }

    public void setLstChoose(List<String> lstChoose) {
        this.lstChoose = lstChoose;
    }

    public List<String> getLstAllAuthor() {
        return lstAllAuthor;
    }

    public void setLstAllAuthor(List<String> lstAllAuthor) {
        this.lstAllAuthor = lstAllAuthor;
    }
}
