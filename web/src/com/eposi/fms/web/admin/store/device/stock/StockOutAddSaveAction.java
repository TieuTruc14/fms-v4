package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Stock;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TieuTruc on 5/7/2015.
 */
public class StockOutAddSaveAction extends AbstractAction {
    private Stock item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        try {
            if(item!=null){
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_OUT_ADD)){
                    addActionError("action.error.permission");
                    return ERROR;
                }
                if (user != null) {
                    Company company= user.getCompany();
                    item.setCompanySource(company);
                    item.setUserCreated(user.getUsername());
                    item.setDateCreated(new Date());
                    item.setDateUpdate(new Date());
                    item.setUserUpdate(user.getUsername());
                    beanFmsDao.addStock(item);

                    // Activity
                    Activity activity = new Activity();
                    activity.setActionName("Addnew");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId("" + item.getId());
                    activity.setObjectName(Stock.class.getName());
                    activity.setIndirectObjectName("/store/device/stock.out.detail.action?id="+item.getId());
                    activity.setDate(new Date());
                    activity.setContext("Thêm mới phiếu xuất Thiết bị:"+item.getId());
                    activity.setPassive(true);
                    activity.setIcon("<i class=\"fa fa-stop time-icon bg-dark\"></i>");

                    //Update log System
//                    beanReportReader.saveActivity(activity);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public Stock getItem() {
        return item;
    }

    public void setItem(Stock item) {
        this.item = item;
    }
}
