package com.eposi.fms.web.admin.manage.contract;


import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by TieuTruc on 3/9/2015.
 */
public class ContractAddNewSaveAction extends AbstractAction {
    private Contract item;
    private String strDateStart;
    private String strDateEnd;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_ADD)){
            addActionError("action.error.permission");
            return INPUT;
        }

        try {
            if(item!=null){
                Vehicle vehicle = beanFmsDao.getVehicle(item.getVehicle().getId());
                if(vehicle==null){
                    addActionMessage("Xe không tồn tại trên hệ thống !");
                    return INPUT;
                }
                if(!beanFmsDao.isParent(vehicle.getCompany(),user.getCompany())){
                    addActionMessage("Bạn không có quyền quản lý xe này !");
                    return INPUT;
                }

                Date dateStart = null;
                Date dateEnd=null;
                try {
                    dateStart = FormatUtil.parseDate(strDateStart, "dd/MM/yyyy");
                    dateEnd = FormatUtil.parseDate(strDateEnd, "dd/MM/yyyy");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(dateStart.after(dateEnd)){
                    addActionMessage("Ngày bắt đầu lớn hơn ngày kết thúc");
                    return INPUT;
                }

                //Addnew Contract
                item.setDateStart(dateStart);
                item.setDateEnd(dateEnd);
                item.setDateCreated(new Date());
                item.setDateUpdate(new Date());
                item.setCompany(vehicle.getCompany());
                item.setUserCreated(user.getUsername());
                item.setCensored(false);

                boolean isConflict = beanFmsDao.isConflictContract(item);
                if(!isConflict){
                    beanFmsDao.addContract(item);
                    //Update DateStart and DateEnd for vehicle
                    if (vehicle.getDateStart() == null||vehicle.getDateStart().after(dateStart)) {
                        vehicle.setDateStart(item.getDateStart());
                    }
                    Calendar cal1 = Calendar.getInstance();
                    cal1.add(Calendar.DATE, 15);
                    Date nextDate= cal1.getTime();

                    if (vehicle.getDateEnd() == null || vehicle.getDateEnd().before(nextDate)) {
                        vehicle.setDateEnd(nextDate);
                    }
                    vehicle.setDisable(false);//mo khoa neu la xe dang bi khoa. Viec khoa sau 15 ngay thi se co tool chay realtime sau

                    beanFmsDao.editVehicle(vehicle);
                    beanFmsV2Dao.editDateDeviceFromVehicleV3(vehicle);
                    // Activity
                    if(vehicle.getKonexyId()!=null) {
                        Activity activity = new Activity();
                        activity.setActionName("AddNew");
                        activity.setActorName(user.getUsername());
                        activity.setObjectId(vehicle.getId());
                        activity.setObjectName(Contract.class.getName());
                        activity.setIndirectObjectName("/manage/contract/list.action?vehicleId="+item.getVehicle().getId());
                        activity.setDate(new Date());
                        activity.setContext("Cập nhật hợp đồng biển số:"+vehicle.getId()+" từ "+FormatUtil.formatDate(dateStart,"yyyy/MM/dd")+" tới "+FormatUtil.formatDate(dateEnd,"yyyy/MM/dd"));
                        activity.setPassive(true);

                         //Update log Company
                        beanReportReader.saveActivity(vehicle.getKonexyId(), activity);
                    }
                    addActionMessage("Thêm mới hợp đồng thành công!!!");
                }else {
                    addActionMessage("Hợp đồng thêm mới bị mất đồng bộ về thời gian hợp đồng !");
                }
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public Contract getItem() {
        return item;
    }

    public void setItem(Contract item) {
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
