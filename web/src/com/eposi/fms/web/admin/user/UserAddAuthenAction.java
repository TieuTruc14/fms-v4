package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Group;
import com.eposi.fms.model.GroupMember;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class UserAddAuthenAction extends AbstractAction {
    private static final long serialVersionUID = 1179907217150470379L;

    private User item;
    private String username;
    private String companyId;
    private List<Group> lstChoose=new ArrayList<>();
    private List<Group> lstAllAuthor=new ArrayList<>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null) {
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_USER_EDIT)){
                addActionError("action.error.permission");
                return ERROR;
            }

            if (StringUtils.isNotEmpty(username)) {
                item = beanFmsDao.getUser(username);
                List<GroupMember> groupsAll=beanFmsDao.searchGroupMemberByUserName(user.getUsername());
                List<GroupMember> groupsChose=beanFmsDao.searchGroupMemberByUserName(username);
                List<String> lstGroupAll=new ArrayList<>();
                List<String> lstGroupChoose=new ArrayList<>();
                if(groupsAll.size()==0) return SUCCESS;
                for(GroupMember itemG:groupsAll){
                    lstGroupAll.add(itemG.getGroupId());
                }
                if(groupsChose.size()>0){
                  for(GroupMember itemchoose: groupsChose){
                      lstGroupChoose.add(itemchoose.getGroupId());
                      lstGroupAll.remove(itemchoose.getGroupId());
                  }
                }
                if(lstGroupAll.size()>0) {
                    lstAllAuthor = beanFmsDao.getGroupByListId(lstGroupAll);
                }
                if(lstGroupChoose.size()>0) {
                    lstChoose = beanFmsDao.getGroupByListId(lstGroupChoose);
                }

            }
        }
        return SUCCESS;
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


    public List<Group> getLstChoose() {
        return lstChoose;
    }

    public void setLstChoose(List<Group> lstChoose) {
        this.lstChoose = lstChoose;
    }

    public List<Group> getLstAllAuthor() {
        return lstAllAuthor;
    }

    public void setLstAllAuthor(List<Group> lstAllAuthor) {
        this.lstAllAuthor = lstAllAuthor;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
