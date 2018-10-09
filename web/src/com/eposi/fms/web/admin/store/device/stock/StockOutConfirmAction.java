package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TieuTruc on 5/8/2015.
 */
public class StockOutConfirmAction extends AbstractAction {
    private String stockId;
    private String companyId;
    private String companyCode;
    private String message="";

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_DEVICE_EDIT)){
                message=getTitleText("fms.failed.authority");
                return SUCCESS;
            }
            if (user != null) {
                Company companyUser= user.getCompany();
                if (StringUtils.isNotEmpty(companyId.trim()) || StringUtils.isNotEmpty(companyCode)) {
                    Company company=new Company();
                    if(StringUtils.isNotEmpty(companyCode)){
                        company=beanFmsDao.getCompany(companyCode);
                    }else{
                        company=beanFmsDao.getCompany(companyId.trim());
                    }

                    if (company != null) {
                        if(company.getId().equals(companyUser.getId())){
                            return SUCCESS;
                        }
                        Stock stock = beanFmsDao.getStock(Long.parseLong(stockId));
                        List<StockDetail> stockDetailList = beanFmsDao.searchStockDetail(stock);
                        if(stockDetailList==null || !(stockDetailList.size()>0)){
                            return INPUT;
                        }
                        if(!company.isOrgazation()){
                            List<Device> deviceList = new ArrayList<Device>();

                            for (StockDetail item : stockDetailList) {
                                Device device = item.getDevice();
                                device.setStock(false);
                                device.setCompany(company);
                                deviceList.add(device);
                            }
                            beanFmsDao.saveOrUpdateDevices(deviceList);
                            stock.setStatus(2);//nhận luôn
                        }else{
                            stock.setStatus(1);//đã xuất-chưa nhận
                        }

                        stock.setCompanyDes(company);
                        beanFmsDao.editStock(stock);
                    }else{
                        return INPUT;
                    }
                }else{
                    return INPUT;
                }
            }

        }catch (Exception e){
            System.out.println("Error Method StockOutConfirmAction.execute:" +e.getMessage());
        }

        return SUCCESS;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
