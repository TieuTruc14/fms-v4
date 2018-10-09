package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Stock;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Created by TienManh on 6/30/2016.
 */
public class StockInUnReceiveAction extends AbstractAction{
    private String id;
    private String note;

    public String execute(){
        FmsDao beanFmsDao=(FmsDao)this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_IN_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        if(id==null || StringUtils.isEmpty(id)){
            return INPUT;
        }
        try{
            Stock stock=beanFmsDao.getStock(Long.parseLong(id));
            if(stock==null) return INPUT;

            if(!stock.getCompanyDes().getId().equals(user.getCompany().getId())){
                addActionError("action.error.permission");
                return ERROR;
            }
            stock.setCompanyDes(stock.getCompanySource());
            stock.setStatus(3);
            stock.setNote(stock.getNote()+"--"+user.getCompany().getName()+" ko nhận--lý do: "+note);

            beanFmsDao.editStock(stock);
        }catch (Exception e){
            System.out.println("StockInUnReceiveAction"+e.getMessage());
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
