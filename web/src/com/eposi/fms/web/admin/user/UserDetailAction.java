package com.eposi.fms.web.admin.user;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.GroupMember;
import com.eposi.fms.model.GroupPermission;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserDetailAction extends AbstractAction {
    private static final long serialVersionUID = -7300338218390706723L;

    private String   username;
    private User     item;
    private String   group;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            if (StringUtils.isNotEmpty(username)){
                item = beanFmsDao.getUser(username);
                List<GroupMember> groupMembers = beanFmsDao.searchGroupMemberByUserName(username);
                List<GrantedAuthority> lstAuths = new ArrayList<GrantedAuthority>();
                if(groupMembers!=null && groupMembers.size()>0){
                    for (GroupMember item: groupMembers){
                        List<GroupPermission> groupPermissions =beanFmsDao.searchGroupPermissionByGroupId(item.getGroupId());
                        if(groupPermissions.size()>0) {
                            for (GroupPermission permission: groupPermissions) {
                                lstAuths.add(new SimpleGrantedAuthority(permission.getPermissionId()));
                            }
                        }
                    }
                }

                item.setGrantedAuths(lstAuths);
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.user.UserDetailAction.details" + e.getMessage());
        }

		return SUCCESS;
	}

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
    }
}
