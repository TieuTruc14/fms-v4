package com.eposi.fms.web.admin.store.device;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class DeviceDetailAction extends AbstractAction {

    private static final long serialVersionUID = 8940121396650885291L;
    private String id;
    private String strDateStart;
    private String strDateEnd;
    private Device item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_VIEW)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        
        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getDevice(id);
                setStrDateStart(FormatUtil.formatDate(item.getDateActive(),"dd/MM/yyyy"));
                setStrDateEnd(FormatUtil.formatDate(item.getDateEnd(),"dd/MM/yyyy"));
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.BatchAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Device getItem() {
        return item;
    }

    public void setItem(Device item) {
        this.item = item;
    }

    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public String getStrDateEnd() {
        return strDateEnd;
    }

    public void setStrDateEnd(String strDateEnd) {
        this.strDateEnd = strDateEnd;
    }
}
