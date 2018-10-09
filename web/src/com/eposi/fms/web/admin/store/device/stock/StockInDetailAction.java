package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

/**
 * Created by TienManh on 6/30/2016.
 */
public class StockInDetailAction extends AbstractAction {
    private String id;
    private Stock item;
    private List<GroupDevice> lstMap=new ArrayList<>();
    private String message="";

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_IN_VIEW)){
                addActionError("action.error.permission");
                return ERROR;
            }

            if (user != null) {
                if (StringUtils.isNotEmpty(id)) {
                    item = beanFmsDao.getStock(Long.parseLong(id));
                    List<StockDetail> stockDetailList=beanFmsDao.searchStockDetail(item);
                    if(stockDetailList.size()>0){
                        Map<String, GroupDevice > groups = new LinkedHashMap<>();
                        for(StockDetail stock:stockDetailList){
                            String key = stock.getDevice().getBatch().getModel().getProductType().getId();
                            GroupDevice group = groups.get(key);
                            if(group==null){
                                group = new GroupDevice();
                                group.setProductType(stock.getDevice().getBatch().getModel().getProductType());
                                group.setDeviceList(new ArrayList<>());
                            }
                            group.getDeviceList().add(stock.getDevice());
                            groups.put(key, group);
                        }

                        Iterator<String> iterator = groups.keySet().iterator();
                        while (iterator.hasNext()){
                            String key = iterator.next();
                            lstMap.add(groups.get(key));
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.addmin.device.stock.detail.execute: "+e.getMessage());
        }
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Stock getItem() {
        return item;
    }

    public void setItem(Stock item) {
        this.item = item;
    }

    public List<GroupDevice> getLstMap() {
        return lstMap;
    }

    public void setLstMap(List<GroupDevice> lstMap) {
        this.lstMap = lstMap;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class GroupDevice {
        private ProductType productType;
        private List<Device> deviceList;

        public ProductType getProductType() {
            return productType;
        }

        public void setProductType(ProductType productType) {
            this.productType = productType;
        }

        public List<Device> getDeviceList() {
            return deviceList;
        }

        public void setDeviceList(List<Device> deviceList) {
            this.deviceList = deviceList;
        }
    }
}
