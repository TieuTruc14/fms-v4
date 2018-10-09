package com.eposi.fms.web.admin.manage.grant;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.GroupGrant;
import com.eposi.fms.model.GroupPermission;
import com.eposi.fms.model.Permission;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TienManh on 7/15/2016.
 */
public class GroupGrantAction extends AbstractAction {
    private static final long serialVersionUID = -7261000867488473048L;

    private String groupId;
    private List<GroupGrantItem> lstGrants=new ArrayList<>();
    private HashMap<String,GroupGrantItem> map=new HashMap<>();

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_GROUP_ADD)) {
            return SUCCESS;
        }
        if(groupId==null || StringUtils.isEmpty(groupId)) return SUCCESS;
        //load all groupgrant database
        List<GroupGrant> lstGroupGrantDB=beanFmsDao.listGroupGrant();
        //gan toan bộ vào Hashmap theo groupgrantItem
        for(GroupGrant groupGrant:lstGroupGrantDB){
            GroupGrantItem groupItem=new GroupGrantItem();
            groupItem.setId(groupGrant.getId());
            groupItem.setName(groupGrant.getName());
            groupItem.setDescription(groupGrant.getDescription());
            map.put(groupItem.getId(),groupItem);
        }
        //load all permission cua groupId này
        List<GroupPermission> lstGroupPermission=beanFmsDao.searchGroupPermissionByGroupId(groupId);
        List<String> lstStringpermissionId=new ArrayList<>();
        for(GroupPermission groupPermission:lstGroupPermission){
            lstStringpermissionId.add(groupPermission.getPermissionId());
        }
        List<Permission> lstPermission=beanFmsDao.searchPermissionByListId(lstStringpermissionId);
        //tu list permision gán được check item cua map
        for(Permission item:lstPermission){
            GroupGrantItem groupGrantItem=map.get(item.getGroupGrant());
            if(groupGrantItem!=null){
                String function=item.getId().replace(item.getGroupGrant(),"");
                switch (function){
                    case "VIEW": groupGrantItem.setView(true);break;
                    case "ADD": groupGrantItem.setAdd(true);break;
                    case "EDIT": groupGrantItem.setEdit(true);break;
                    case "DELETE": groupGrantItem.setDelete(true);break;
                }
                map.put(item.getGroupGrant(),groupGrantItem);
            }
        }
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            lstGrants.add(map.get(key));
        }

        return SUCCESS;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<GroupGrantItem> getLstGrants() {
        return lstGrants;
    }

    public void setLstGrants(List<GroupGrantItem> lstGrants) {
        this.lstGrants = lstGrants;
    }

    public HashMap<String, GroupGrantItem> getMap() {
        return map;
    }

    public void setMap(HashMap<String, GroupGrantItem> map) {
        this.map = map;
    }

    public static class GroupGrantItem {
        private String id;
        private String name;
        private String description;
        private boolean view;
        private boolean add;
        private boolean edit;
        private boolean delete;

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

        public boolean isView() {
            return view;
        }

        public void setView(boolean view) {
            this.view = view;
        }

        public boolean isAdd() {
            return add;
        }

        public void setAdd(boolean add) {
            this.add = add;
        }

        public boolean isEdit() {
            return edit;
        }

        public void setEdit(boolean edit) {
            this.edit = edit;
        }

        public boolean isDelete() {
            return delete;
        }

        public void setDelete(boolean delete) {
            this.delete = delete;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
