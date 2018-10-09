package com.eposi.fms.web.admin.manage.contract;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Contract;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by TienManh on 7/10/2016.
 */
public class ContractCensoredSaveAction extends AbstractAction {

    private static final long serialVersionUID = -6704757506626553434L;
    private String id;

    public String execute(){

        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_TYPE_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if(id==null || StringUtils.isEmpty(id)) return SUCCESS;
        Contract item=beanFmsDao.getContract(Long.parseLong(id));
        if(item!=null && !item.isCensored()){
            item.setCensored(true);
            item.setCensoredDate(new Date());
            item.setUserUpdated(user.getUsername());

            Vehicle vehicle=item.getVehicle();
            if(item.getDateStart()!=null){
                try {
//                    Calendar cal=Calendar.getInstance();
//                    cal.setTime(vehicle.getDateStart());
//                    cal.add(Calendar.YEAR, 1);
//                    Date nextYear=cal.getTime();
                    vehicle.setDateStart(item.getDateStart());
                    vehicle.setDateEnd(item.getDateEnd());
                    if(item.getDateEnd().after(new Date())){
                        vehicle.setDisable(false);//mo khoa xe neu la xe lau ngay
                    }

                    beanFmsDao.editVehicle(vehicle);
                    beanFmsDao.editContract(item);

                    //edit v2
                    beanFmsV2Dao.editDateDeviceFromVehicleV3(vehicle);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
