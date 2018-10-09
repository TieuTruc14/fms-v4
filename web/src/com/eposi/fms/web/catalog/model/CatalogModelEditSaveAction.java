package com.eposi.fms.web.catalog.model;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Model;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class CatalogModelEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 2131401253745105041L;
    private Model item;

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_MODEL_EDIT)){
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if (item == null) {
            return SUCCESS;
        }
        if (item.getId() == null) {
            return SUCCESS;
        }
        if(beanFmsDao.getModel(item.getId())==null){
            return  SUCCESS;
        }

        try {
            Model oldItem=beanFmsDao.getModel(item.getId());
            if(oldItem!=null){
               oldItem.setDateUpdate(new Date());
                oldItem.setUserUpdate(user.getUsername());
                oldItem.setName(item.getName());
                oldItem.setProductType(item.getProductType());
                oldItem.setNote(item.getNote());

                beanFmsDao.editModel(oldItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        super.validate();
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public Model getItem() {
        return item;
    }

    public void setItem(Model item) {
        this.item = item;
    }

}
