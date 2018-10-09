package com.eposi.fms.web.admin.store.batch.device;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.RegexUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.math.ProductKeyUtil;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class BatchDeviceAddNewSaveAction extends AbstractAction {

    private static final long serialVersionUID = 1528811516257154303L;
    private Device device;
    private String strDateStartForm;
    private String batchId;
    private String message="";
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_ADD)){
            message=getTitleText("fms.failed.authority");
            return SUCCESS;
        }
        if (device == null) {
            return INPUT;
        }
        if (device.getId() == null) {
            return INPUT;
        }

        if(!RegexUtil.validateDevice(device.getId())){
            return INPUT;
        }

        if (strDateStartForm == null || strDateStartForm.isEmpty() || batchId.isEmpty()) {
            return INPUT;
        }


        Date dateStart = null;
        try {
            dateStart = FormatUtil.parseDate(strDateStartForm, "dd/MM/yyyy");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Batch batch=beanFmsDao.getBatch(batchId);
            if(batch==null) return INPUT;

            Device deviceCheck= beanFmsDao.getDevice(device.getId());
            if(deviceCheck!=null) return INPUT;

            Company company = user.getCompany();
            if (company != null) {
                device.setDateCreated(new Date());
                device.setDateStart(dateStart);
                device.setCompany(company);
                device.setUserCreated(user.getUsername());
                device.setBatch(batch);
                /**
                 * Generate  product_key
                 */
                Device checkDeviceKey=null;
                String key="";
                int count=0;
                while(count<10){
                    key = ProductKeyUtil.genKey(batch.getModel().getId(), batch.getCode());
                    checkDeviceKey=beanFmsDao.getDeviceByProductKey(key);
                    if(checkDeviceKey==null){
                        break;
                    }
                    count++;
                }

                if(checkDeviceKey!=null){
                    addActionMessage("Không thêm được vào hệ thống. Hãy thử lại");
                    return INPUT;
                }
                device.setProduct_key(key);
                //Create KonexyID for logging activity
                String description = "{ \"name\":\"" + device.getId() + "\"}";
                String konexyId = beanReportReader.newKonexyId(description);
                device.setKonexyId(konexyId);

                beanFmsDao.addDevice(device);
                // Activity
                if(device.getKonexyId()!=null) {
                    Activity activity = new Activity();
                    activity.setId(user.getCompany().getId());
                    activity.setActionName("Addnew");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(device.getId());
                    activity.setObjectName(Device.class.getName());
                    activity.setIndirectObjectName("/store/device/activity.action?deviceId=" + device.getId());
                    activity.setDate(new Date());
                    activity.setContext("Thêm mới thiết bị:" + device.getId());
                    activity.setPassive(true);
                    //Update log Device
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


    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getStrDateStartForm() {
        return strDateStartForm;
    }

    public void setStrDateStartForm(String strDateStartForm) {
        this.strDateStartForm = strDateStartForm;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
