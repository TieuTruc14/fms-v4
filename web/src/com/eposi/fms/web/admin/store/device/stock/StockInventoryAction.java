package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Device;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class StockInventoryAction extends AbstractAction {
    private List<CompanyItem> lstCompanyItem = new ArrayList<CompanyItem>();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, CompanyItem> items = new LinkedHashMap<>();
        try {
            List<Device>  lstDevice = beanFmsDao.getDeviceInOrganizationAndUnit(user.getCompany(), 0);
            for(Device device: lstDevice){
                String key = device.getCompany().getId();
                CompanyItem item = items.get(key);
                if(item==null){
                    item = new CompanyItem();
                    item.setId(key);
                    item.setName(device.getCompany().getName());
                }
                item.setDeviceUnused(item.getDeviceUnused()+1);
                items.put(key, item);
            }

           Iterator<String> keys = items.keySet().iterator();
            while (keys.hasNext()){
                String key = keys.next();
                lstCompanyItem.add(items.get(key));
            }

        } catch (Exception e){
            System.out.println("com.eposi.fms.web.addmin.device.stock.excute: "+e.getMessage());
        }

        return SUCCESS;
    }

    public List<CompanyItem> getLstCompanyItem() {
        return lstCompanyItem;
    }

    public void setLstCompanyItem(List<CompanyItem> lstCompanyItem) {
        this.lstCompanyItem = lstCompanyItem;
    }

    public static class CompanyItem{
        private String id;
        private String name;
        private int    deviceUnused;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public int getDeviceUnused() {
            return deviceUnused;
        }

        public void setDeviceUnused(int deviceUnused) {
            this.deviceUnused = deviceUnused;
        }
    }
}
