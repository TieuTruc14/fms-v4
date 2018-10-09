package com.eposi.fms.security;


import com.eposi.fms.model.*;
import com.eposi.fms.common.AbstractBean;
import com.eposi.fms.model.User;

import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class FmsUserDetailsService extends AbstractBean implements UserDetailsService {
    private static final long serialVersionUID = 891374517584282728L;

    @Override
    public User loadUserByUsername(String username){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");

        User user = beanFmsDao.getUser(username);
        if (user != null) {
            Company company = user.getCompany();
            if(company.isOrgazation()) {
                List<GrantedAuthority> lstAuths = new ArrayList<GrantedAuthority>();
                HashMap<String, String> map = new HashMap<>();
                List<GroupMember> groupMembers = beanFmsDao.searchGroupMemberByUserName(username);
                if (groupMembers != null && groupMembers.size() > 0) {
                    for (GroupMember item : groupMembers) {
                        List<GroupPermission> groupPermissions = beanFmsDao.searchGroupPermissionByGroupId(item.getGroupId());
                        if (groupPermissions.size() > 0) {
                            for (GroupPermission permission : groupPermissions) {
                                if (map.get(permission.getPermissionId()) == null) {
                                    map.put(permission.getPermissionId(), permission.getPermissionId());
                                }
                            }
                        }
                    }
                }
                Iterator<String> keys = map.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    lstAuths.add(new SimpleGrantedAuthority(map.get(key)));
                }

                user.setGrantedAuths(lstAuths);
                // Activity
                Activity activity = new Activity();
                activity.setActionName("Login");
                activity.setActorName(user.getUsername());
                activity.setObjectId(user.getUsername());
                activity.setObjectName(User.class.getName());
                activity.setIndirectObjectName("/user/detail.action?id=" + user.getUsername());
                activity.setDate(new Date());
                activity.setContext("System begin login");
                activity.setPassive(true);
                //Update log Company
                if (user.getKonexyId() != null) {
                    beanReportReader.saveActivity(user.getKonexyId(), activity);
                }
            }else {
                user =null;
                System.out.println("username: "+username+" not authority !");
                throw new UsernameNotFoundException("username " + username+ " not authority !");
            }
        }else {
            System.out.println("username: "+username+" not found !");
            throw new UsernameNotFoundException("username " + username+ " not found");
        }

        return user;
    }
}
