package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Stock;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TieuTruc on 5/7/2015.
 */
public class StockOutEditAction extends AbstractAction {
    private String id;
    private Stock item;

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_OUT_EDIT)){
                addActionError("action.error.permission");
                return SUCCESS;
            }
            if (user != null) {
                if (StringUtils.isNotEmpty(id)) {
                    item=beanFmsDao.getStock(Long.parseLong(id));
                }
            }

        }catch (Exception e){
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

    public Stock getItem() {
        return item;
    }

    public void setItem(Stock item) {
        this.item = item;
    }
}
