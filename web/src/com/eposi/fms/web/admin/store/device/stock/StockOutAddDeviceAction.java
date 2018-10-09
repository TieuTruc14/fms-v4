package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TieuTruc on 5/7/2015.
 */
public class StockOutAddDeviceAction extends AbstractAction {
    private String id;

    public String execute(){
        return SUCCESS;
    }
    public List<Device> getDeviceList(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        List<Device> devices= new ArrayList<Device>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null){
            Company company= user.getCompany();
            devices=beanFmsDao.searchDeviceCompanyToAddStock(company, 0, false);
        }
        return devices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
