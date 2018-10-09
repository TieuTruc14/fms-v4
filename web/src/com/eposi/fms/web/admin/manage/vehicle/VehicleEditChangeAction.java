package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class VehicleEditChangeAction extends AbstractAction {
    private static final long serialVersionUID = -7272672696871750328L;

    private String vehicleId;
    private String note;
    private Vehicle item;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }

        if(beanFmsDao.getVehicle(vehicleId)!=null){
            addActionError("action.error.vehicle.exist");
            return ERROR;
        }

        try {
            if (item != null) {
                Vehicle newVehicle = beanFmsDao.getVehicle(item.getId());
                Vehicle oldVehicle = beanFmsDao.getVehicle(item.getId());
                if(newVehicle==null){
                    addActionError("action.error.permission");
                    return ERROR;
                }

                if(!beanFmsDao.isParent(newVehicle.getCompany(), user.getCompany())){
                    addActionError("action.error.permission");
                    return ERROR;
                }

                newVehicle.setNote(newVehicle.getNote() + " --Đổi biển từ " + newVehicle.getId() + " ngày " + FormatUtil.formatDate(new Date(), "dd/MM/yyyy") + "( Lý do:" + note + ")");
                newVehicle.setUserUpdated(user.getUsername());
                newVehicle.setDateUpdated(new Date());

                beanFmsDao.editChangeVehicle(newVehicle,oldVehicle, vehicleId);
                //Edit Change V2
                beanFmsV2Dao.editChangeVehicle(oldVehicle, vehicleId, newVehicle.getNote());

                // update Activity
                if(newVehicle.getKonexyId()!=null) {
                    Activity activity = new Activity();
                    activity.setActionName("Update");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(vehicleId);
                    activity.setObjectName(Vehicle.class.getName());
                    activity.setIndirectObjectName("/manage/vehicle/vehicle.detail.action?vehicleId=" + vehicleId);
                    activity.setDate(new Date());
                    activity.setContext("Đổi biển số từ " + item.getId() + " sang biển " + vehicleId + "--Lý do: " + note);
                    activity.setPassive(true);

                    beanReportReader.saveActivity(newVehicle.getKonexyId(), activity);

                    //Send message Activity to server Aggregate
                    byte[] bytes = conf.asByteArray(activity);
                    beanQueueingConsumerReportClient.put(bytes);
                }
                addActionMessage("Cập nhật phương tiện thành công!!!");
            } else {
                return INPUT;
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            addActionMessage("Cập nhật thất bại!!!");
            return INPUT;
        }

        return SUCCESS;
	}

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Vehicle getItem() {
        return item;
    }

    public void setItem(Vehicle item) {
        this.item = item;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
