package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.Stock;
import com.eposi.fms.model.StockDetail;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TienManh on 6/30/2016.
 */
public class StockDeleteAction extends AbstractAction {
    private String id;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_IN_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(id==null || StringUtils.isEmpty(id)){
            return INPUT;
        }
        try{
            Stock stock=beanFmsDao.getStock(Long.parseLong(id));
            if(stock==null || !stock.getCompanyDes().getId().equals(user.getCompany().getId())){
                addActionError("action.error.permission");
                return ERROR;
            }
            //get all device of stock
            List<StockDetail> stockDetailList = beanFmsDao.searchStockDetail(stock);
            if(stockDetailList!=null && stockDetailList.size()>0){
                List<Device> deviceList = new ArrayList<>();

                for (StockDetail item : stockDetailList) {
                    Device device = item.getDevice();
                    device.setStock(false);
                    deviceList.add(device);
                }
                beanFmsDao.saveOrUpdateDevices(deviceList);
            }

            stock.setStatus(4);
            stock.setDateUpdate(new Date());
            stock.setUserUpdate(user.getUsername());
            beanFmsDao.editStock(stock);
        }catch (Exception e){
            System.out.println("StockDeleteAction"+e.getMessage());
            e.printStackTrace();
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
