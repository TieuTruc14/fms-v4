package com.eposi.fms.web.admin.manage.vehicle;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.v2.model.Device;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Date;
import java.util.List;

public class VehicleEditAction extends AbstractAction {
    private static final long serialVersionUID = -6036633282107123996L;

    private Vehicle item;
    private String message="";
    private String companyId="";

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_VEHICLE_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try {
            if (item != null) {
                item.setDateUpdated(new Date());
                Vehicle oldVehicle = beanFmsDao.getVehicle(item.getId());
                if(oldVehicle==null){
                    return INPUT;
                }
                if(!beanFmsDao.isParent(oldVehicle.getCompany(),user.getCompany())){
                    addActionError("action.error.permission");
                    return INPUT;
                }
                if(item.getFuelType().getId()==null || item.getType().getId()==null){
                    message="Chú ý: Hãy nhập đầy đủ thông tin để tiếp tục!";
                    return INPUT;
                }

                if(user==null) return INPUT;
                oldVehicle= mergeVehicle(oldVehicle,item);
                beanFmsDao.editVehicle(oldVehicle);
                Device itemV2=beanFmsV2Dao.getDevice(oldVehicle.getId());
                if(itemV2!=null) beanFmsV2Dao.mergeDeviceFromVehicleV3(oldVehicle, itemV2);
                addActionMessage("Cập nhật phương tiện thành công!!!");

//              update vehicle Activity
                if(oldVehicle.getKonexyId()!=null) {
                    Activity activity = new Activity();
                    activity.setActionName("Update");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(item.getId());
                    activity.setObjectName(Vehicle.class.getName());
                    activity.setIndirectObjectName("/manage/vehicle/vehicle.detail.action?vehicleId=" + item.getId());
                    activity.setDate(new Date());
                    activity.setContext("Cập nhật Phương tiện");
                    activity.setPassive(true);

                    //Update Activity Vehicle
                    beanReportReader.saveActivity(oldVehicle.getKonexyId(), activity);
                }
            } else {
                return INPUT;
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }

        return SUCCESS;
	}


    private Vehicle mergeVehicle(Vehicle newVehicle,Vehicle itemInfor){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newVehicle.setType(itemInfor.getType());
        newVehicle.setCapacity(itemInfor.getCapacity());
        newVehicle.setConfigI0(itemInfor.isConfigI0());
        newVehicle.setConfigI1(itemInfor.isConfigI1());
        newVehicle.setConfigI2(itemInfor.isConfigI2());
        newVehicle.setConfigI5(itemInfor.isConfigI5());
        newVehicle.setOnFilter(itemInfor.isOnFilter());
        newVehicle.setWorkVehicleMapping(itemInfor.getWorkVehicleMapping());
        newVehicle.setSensor(itemInfor.isSensor());
        newVehicle.setBgt(itemInfor.isBgt());
        newVehicle.setDateUpdated(new Date());
        newVehicle.setDisable(itemInfor.isDisable());
        newVehicle.setUserUpdated(user.getUsername());
        newVehicle.setNote(itemInfor.getNote());
        newVehicle.setFuelType(itemInfor.getFuelType());
        if(itemInfor.getDriver().getId()!=null && StringUtils.isNotEmpty(itemInfor.getDriver().getId())){
            newVehicle.setDriver(itemInfor.getDriver());
        }else{
            newVehicle.setDriver(null);
        }

        return newVehicle;
    }

    public Vehicle getItem() {
        return item;
    }

    public void setItem(Vehicle item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<Driver> getDrivers(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(companyId)) {
            Company company = beanFmsDao.getCompany(companyId);
            return beanFmsDao.searchDriverByCompany(company);
        }else{
            Company company=beanFmsDao.getCompany(item.getCompany().getId());
            return beanFmsDao.searchDriverByCompany(company);
        }
    }
}
