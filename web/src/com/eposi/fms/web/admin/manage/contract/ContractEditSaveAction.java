package com.eposi.fms.web.admin.manage.contract;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by TieuTruc on 3/9/2015.
 */
public class ContractEditSaveAction extends AbstractAction {
    private Contract item;
    private String id;
    private String strDateStart;
    private String strDateEnd;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_EDIT)){
            addActionError("action.error.permission");
            return INPUT;
        }
        try {
            if(item!=null){
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
                    return SUCCESS;
                }

                Vehicle vehicle = beanFmsDao.getVehicle(item.getVehicle().getId());
                if(vehicle!=null) {
                    if(!beanFmsDao.isParent(vehicle.getCompany(), user.getCompany())){
                        return INPUT;
                    }

                    /*edit*/
                    Contract prevContract = beanFmsDao.getContract(item.getId());
                    item.setDateStart(dateStart);
                    item.setDateEnd(dateEnd);
                    item.setCompany(vehicle.getCompany());
                    item.setDateUpdate(new Date());
                    item.setDateCreated(prevContract.getDateCreated());
                    item.setUserCreated(prevContract.getUserCreated());

                    boolean isConflict = beanFmsDao.isConflictContract(item);
                    if(!isConflict){
                        beanFmsDao.editContract(item);
                        // Activity
                        if(vehicle.getKonexyId()!=null) {
                            Activity activity = new Activity();
                            activity.setActionName("Edit");
                            activity.setActorName(user.getUsername());
                            activity.setObjectId(vehicle.getId());
                            activity.setObjectName(Contract.class.getName());
                            activity.setIndirectObjectName("/manage/contract/list.action?vehicleId=" + vehicle.getId());
                            activity.setDate(new Date());
                            activity.setContext("Cập nhật hợp đồng biển số:" + vehicle.getId() + " từ " + FormatUtil.formatDate(dateStart, "yyyy/MM/dd") + " tới " + FormatUtil.formatDate(dateEnd, "yyyy/MM/dd"));
                            activity.setPassive(true);
                            activity.setIcon("<i class=\"fa fa-stop time-icon bg-dark\"></i>");
                            //Update log Company
                            beanReportReader.saveActivity(vehicle.getKonexyId(), activity);
                        }

                        if (vehicle.getDateStart() == null) {
                            vehicle.setDateStart(item.getDateStart());
                        }
                        Calendar cal1 = Calendar.getInstance();
                        cal1.setTime(item.getDateStart());
                        cal1.add(Calendar.DATE, 15);//them 15 ngay so voi ngay bat dau
                        Date nextDate= cal1.getTime();

                        if (vehicle.getDateEnd() == null || vehicle.getDateEnd().before(nextDate)) {
                            vehicle.setDateEnd(nextDate);
                        }
                        if((new Date()).before(nextDate)){
                            vehicle.setDisable(false);//mo khoa neu la xe dang bi khoa. Viec khoa sau 15 ngay thi se co tool chay realtime sau
                        }


                        beanFmsDao.editVehicle(vehicle);

                        addActionMessage("Cập nhật hợp đồng thành công!!!");
                    }else {
                        addActionMessage("Hợp đồng thêm mới bị mất đồng bộ về thời gian hợp đồng!");
                    }
                }else{
                    addActionMessage("Biển số xe không tồn tại!");
                    return INPUT;
                }
            }else{
                if(id!=null) {
                    item = beanFmsDao.getContract(Long.parseLong(id));
                    setStrDateStart(FormatUtil.formatDate(item.getDateStart(), "dd/MM/yyyy"));
                    setStrDateEnd(FormatUtil.formatDate(item.getDateEnd(), "dd/MM/yyyy"));
                }
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    public Contract getItem() {
        return item;
    }

    public void setItem(Contract item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
