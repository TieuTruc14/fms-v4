package com.eposi.fms.web.admin.store.batch.device;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Batch;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class BatchDeviceEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 8650582305269574888L;

    private Device device;
    private String strDateStart;
    private String batchId;
    private String message="";
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        if (device == null || device.getId() == null || strDateStart == null || strDateStart.isEmpty() ||batchId.isEmpty()) {
            return SUCCESS;
        }

        if(beanFmsDao.getDevice(device.getId())==null){
            return SUCCESS;
        }

        Date dateStart = null;
        try {
            dateStart   = FormatUtil.parseDate(strDateStart, "dd/MM/yyyy");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(! beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_EDIT)){
                message=getTitleText("fms.failed.authority");
                return SUCCESS;
            }
            Device item=beanFmsDao.getDevice(device.getId());
            if(item!=null){
                if(beanFmsDao.isParent(item.getCompany(),user.getCompany())) {
                    item.setDateStart(dateStart);
                    item.setDescription(device.getDescription());
                    item.setDateUpdated(new Date());
                    item.setUserUpdated(user.getUsername());

                    beanFmsDao.editDevice(item);
                    // Activity
                    if (item.getKonexyId() != null) {
                        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
                        Activity activity = new Activity();
                        activity.setId(user.getCompany().getId());
                        activity.setActionName("Edit device");
                        activity.setActorName(user.getUsername());
                        activity.setObjectId(item.getId());
                        activity.setObjectName(Device.class.getName());
                        activity.setIndirectObjectName("/store/device/activity.action?deviceId=" + item.getId());
                        activity.setDate(new Date());
                        activity.setContext("Cập nhật thiết bị:" + item.getId());
                        activity.setPassive(true);
                        activity.setIcon("<i class=\"fa fa-stop time-icon bg-dark\"></i>");

                        //Update log Company
                        beanReportReader.saveActivity(item.getKonexyId(), activity);

                        //Send message Activity to server Aggregate
                        byte[] bytes = conf.asByteArray(activity);
                        beanQueueingConsumerReportClient.put(bytes);
                    }
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


    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
