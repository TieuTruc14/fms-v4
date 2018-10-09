package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Stock;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TieuTruc on 5/7/2015.
 */
public class StockOutEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = -8439174182927067389L;
    private Stock item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_OUT_EDIT)){
                addActionError("action.error.permission");
                return ERROR;
            }

            if (user != null) {
                if (item != null && item.getId()>0)  {
                    Stock newStock= beanFmsDao.getStock(item.getId());
                    newStock.setName(item.getName());
                    newStock.setNote(item.getNote());
                    newStock.setUserUpdate(user.getUsername());
                    newStock.setDateUpdate(new Date());
                    beanFmsDao.editStock(newStock);
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
