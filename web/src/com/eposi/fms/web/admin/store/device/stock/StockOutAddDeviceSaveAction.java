package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TieuTruc on 5/7/2015.
 */
public class StockOutAddDeviceSaveAction extends AbstractAction {

    private String id;
    private List<DeviceItem> devices=new ArrayList<>();
    private List<StockDetail> stockDetailList = new ArrayList<StockDetail>();
    private String message="";

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_DEVICE_ADD)){
            message=getTitleText("fms.failed.authority");
            return INPUT;
        }

        try{
            if(StringUtils.isNotEmpty(id)){
                Stock stock= beanFmsDao.getStock(Long.parseLong(id));
                List<Device> deviceList= new ArrayList<Device>();
                if(devices!=null && devices.size()>0 && stock!=null && user!=null){
                    Company company = user.getCompany();
                    for ( DeviceItem item : devices) {
                        if (StringUtils.equals("on", item.getCheckbox())) {
                            Device device=beanFmsDao.getDevice(item.getId());
                            if(device.getCompany().getId().equals(company.getId())) {
                                if(!device.isStock()) {
                                    device.setStock(true);//xac nhan thiet bi da thuoc phieu xuat
                                    deviceList.add(device);
                                    StockDetail stockDetail = new StockDetail();
                                    stockDetail.setStock(stock);
                                    stockDetail.setDevice(device);
                                    stockDetail.setDateCreated(new Date());
                                    stockDetail.setUserCreated(user);
                                    stockDetailList.add(stockDetail);
                                }
                            }
                        }
                    }
                    if(stockDetailList.size()>0){
                        beanFmsDao.saveOrUpdateDevices(deviceList);
                        beanFmsDao.saveOrUpdateStockDetal(stockDetailList);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.addmin.device.stock.out.add.device.execute: "+e.getMessage());
        }
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DeviceItem> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceItem> devices) {
        this.devices = devices;
    }

    public List<StockDetail> getStockDetailList() {
        return stockDetailList;
    }

    public void setStockDetailList(List<StockDetail> stockDetailList) {
        this.stockDetailList = stockDetailList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DeviceItem{
        private String id;
        private String checkbox;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCheckbox() {
            return checkbox;
        }

        public void setCheckbox(String checkbox) {
            this.checkbox = checkbox;
        }
    }
}
