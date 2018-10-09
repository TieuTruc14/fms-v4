package com.eposi.fms.web.admin.store.device;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.apache.commons.lang.StringUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class DeviceEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 8650582305269574888L;
    private Device item;
    private String strDateActive;
    private String strDateEnd;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if (item == null || item.getId() == null) {
            return SUCCESS;
        }

        Device device=beanFmsDao.getDevice(item.getId());
        if(device==null){
            return  SUCCESS;
        }

        if (strDateActive != null && StringUtils.isNotEmpty(strDateActive) && strDateEnd != null && StringUtils.isNotEmpty(strDateEnd)) {
            Date dateActive = null;
            Date dateEnd = null;
            try {
                dateActive   = FormatUtil.parseDate(strDateActive, "dd/MM/yyyy");
                dateEnd     = FormatUtil.parseDate(strDateEnd, "dd/MM/yyyy");
                if(dateActive.after(dateEnd)) return SUCCESS;
                device.setDateActive(dateActive);
                device.setDateEnd(dateEnd);
            } catch (Exception e) {
                e.printStackTrace();
                return SUCCESS;
            }
        }

        try {

            if(beanFmsDao.isParent(device.getCompany(), user.getCompany())){
                device.setDescription(item.getDescription());
                device.setDateUpdated(new Date());
                device.setUserUpdated(user.getUsername());

                beanFmsDao.editDevice(device);

                // Activity
                if(item.getKonexyId()!=null) {
                    Activity activity = new Activity();
                    activity.setId(user.getCompany().getId());
                    activity.setActionName("Edit device");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(device.getId());
                    activity.setObjectName(Device.class.getName());
                    activity.setIndirectObjectName("/store/device/activity.action?deviceId=" + device.getId());
                    activity.setDate(new Date());
                    activity.setContext("Cập nhật thiết bị:" + device.getId());
                    activity.setPassive(true);
                    activity.setIcon("<i class=\"fa fa-stop time-icon bg-dark\"></i>");

                    //Update log Company
                    ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
                    beanReportReader.saveActivity(device.getKonexyId(), activity);

                    //Send message Activity to server Aggregate
                    byte[] bytes = conf.asByteArray(activity);
                    beanQueueingConsumerReportClient.put(bytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        super.validate();
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public Device getItem() {
        return item;
    }

    public void setItem(Device item) {
        this.item = item;
    }


    public String getStrDateEnd() {
        return strDateEnd;
    }

    public void setStrDateEnd(String strDateEnd) {
        this.strDateEnd = strDateEnd;
    }

    public String getStrDateActive() {
        return strDateActive;
    }

    public void setStrDateActive(String strDateActive) {
        this.strDateActive = strDateActive;
    }
}
