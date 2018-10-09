package com.eposi.fms.web.admin.manage.grant;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.GroupPermission;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TienManh on 7/15/2016.
 */
public class GroupGrantSaveAction extends AbstractAction {
    private static final long serialVersionUID = -8449003413464177622L;

    private List<GroupGrantItem> lstGrants=new ArrayList<>();
    private String groupId;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_GROUP_ADD)) {
            return SUCCESS;
        }
        if(groupId==null || StringUtils.isEmpty(groupId)) return SUCCESS;
        List<GroupPermission> lstAdd=new ArrayList<>();
        beanFmsDao.deleteGroupPermissionByGroupId(groupId);
        for(GroupGrantItem item:lstGrants){
            if (StringUtils.equals("on", item.getView())) {
                GroupPermission groupPermission=new GroupPermission();
                groupPermission.setGroupid(groupId);
                groupPermission.setPermissionId(item.getId() + "VIEW");
                lstAdd.add(groupPermission);
            }
            if (StringUtils.equals("on", item.getAdd())) {
                GroupPermission groupPermission=new GroupPermission();
                groupPermission.setGroupid(groupId);
                groupPermission.setPermissionId(item.getId() + "ADD");
                lstAdd.add(groupPermission);
            }
            if (StringUtils.equals("on", item.getEdit())) {
                GroupPermission groupPermission=new GroupPermission();
                groupPermission.setGroupid(groupId);
                groupPermission.setPermissionId(item.getId() + "EDIT");
                lstAdd.add(groupPermission);
            }
            if (StringUtils.equals("on", item.getDelete())) {
                GroupPermission groupPermission=new GroupPermission();
                groupPermission.setGroupid(groupId);
                groupPermission.setPermissionId(item.getId() + "DELETE");
                lstAdd.add(groupPermission);
            }
        }
        if(lstAdd.size()>0) beanFmsDao.saveOrUpdateGroupPermissions(lstAdd);

        return SUCCESS;
    }

    public List<GroupGrantItem> getLstGrants() {
        return lstGrants;
    }

    public void setLstGrants(List<GroupGrantItem> lstGrants) {
        this.lstGrants = lstGrants;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public static class GroupGrantItem {
        private String id;
        private String name;
        private String view;
        private String add;
        private String edit;
        private String delete;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getEdit() {
            return edit;
        }

        public void setEdit(String edit) {
            this.edit = edit;
        }

        public String getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }
    }
}
