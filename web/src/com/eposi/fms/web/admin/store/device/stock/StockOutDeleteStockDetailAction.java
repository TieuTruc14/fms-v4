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

/**
 * Created by TieuTruc on 5/8/2015.
 */
public class StockOutDeleteStockDetailAction extends AbstractAction {
    private String deviceId;
    private String id;
    private String message="";

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_DEVICE_DELETE)){
            message=getTitleText("fms.failed.authority");
            return INPUT;
        }
        try{
            if(StringUtils.isNotEmpty(deviceId)&&StringUtils.isNotEmpty(id)){
                Stock stock= beanFmsDao.getStock(Long.parseLong(id));
                Device device=beanFmsDao.getDevice(deviceId);
                StockDetail stockDetail=beanFmsDao.searchStockDetailById(stock,device);
                device.setStock(false);
                beanFmsDao.editDevice(device);
                beanFmsDao.deleteStockDetail(stockDetail);
            }

        }catch (Exception e){
            System.out.println("Error  com.eposi.web.addmin.stock.StockOutDeleteStockDetail.execute():" + e.getMessage());
        }
        return SUCCESS;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
