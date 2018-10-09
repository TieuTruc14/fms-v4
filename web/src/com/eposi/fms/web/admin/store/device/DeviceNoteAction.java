package com.eposi.fms.web.admin.store.device;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class DeviceNoteAction extends AbstractAction {
    private static final long serialVersionUID = 5300682213603126166L;
    private Device  item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");

        if(item == null){
            addActionError("action.error.device.null");
            return ERROR;
        }

        try{
            if(item!=null) {
                Device device = beanFmsDao.getDevice(item.getId());
                if(device==null){
                    addActionError("action.error.device.null");
                    return ERROR;
                }
                if(!beanFmsDao.isParent(device.getCompany(), user.getCompany())){
                    addActionError("action.error.permission");
                    return ERROR;
                }
                device.setDescription(item.getDescription());
                beanFmsDao.editDevice(device);
                if(device.getKonexyId()!=null) {
                    Activity activity = new Activity();
                    activity.setIndirectObjectName("/store/device/activity.action?deviceId=" + item.getId());
                    activity.setActionName("Note");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(device.getId());
                    activity.setObjectName(Device.class.getName());
                    activity.setContext(item.getDescription());
                    activity.setDate(new Date());
                    activity.setPassive(true);
                    beanReportReader.saveActivity(device.getKonexyId(), activity);
                }
            }
        }catch (Exception e){
            System.out.println("error: com.eposi.fms.web.admin.store.device.DeviceNoteAction");
            e.printStackTrace();
        }


        return SUCCESS;
	}

    public Device getItem() {
        return item;
    }

    public void setItem(Device item) {
        this.item = item;
    }

}
