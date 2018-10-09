package com.eposi.fms.web.admin.manage.contract;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Contract;
import com.eposi.fms.model.User;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by TienManh on 7/10/2016.
 */
public class ContractCensoredAllAction extends AbstractAction {
    private static final long serialVersionUID = 7583771603233108219L;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_TYPE_ADD)){
            addActionError("action.error.permission");
            return SUCCESS;
        }

        List<Contract> lst=beanFmsDao.searchContractCensored(false);
        if(lst!=null && lst.size()>0){
            List<Vehicle> lstVehicle=new ArrayList<>();
            try {
                for(int i=0;i<lst.size();i++){
                    Contract item=lst.get(i);
                    item.setCensored(true);
                    item.setCensoredDate(new Date());
                    item.setUserUpdated(user.getUsername());

                    Vehicle vehicle=item.getVehicle();
                    if(item.getDateStart()!=null){
                        vehicle.setDateStart(item.getDateStart());
                        vehicle.setDateEnd(item.getDateEnd());
                        vehicle.setDisable(false);//mo khoa neu la xe lau ngay
                        lstVehicle.add(vehicle);
                    }
                }
                beanFmsDao.saveOrUpdateContracts(lst);
                beanFmsDao.saveOrUpdateVehicles(lstVehicle);

                //edit device v2
                for(Vehicle item:lstVehicle){
                    beanFmsV2Dao.editDateDeviceFromVehicleV3(item);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return SUCCESS;
    }
}
