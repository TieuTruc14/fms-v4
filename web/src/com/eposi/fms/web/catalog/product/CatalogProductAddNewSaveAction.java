package com.eposi.fms.web.catalog.product;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.ProductType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CatalogProductAddNewSaveAction extends AbstractAction {
    private static final long serialVersionUID = 9081305752618926969L;

    private ProductType item;


    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_PRODUCT_TYPE_ADD)) {
            addActionError("action.error.permission");
            return SUCCESS;
        }
        if (item == null) {
            return SUCCESS;
        }

        try {
            if(item.getType()==0){
                if(user.getCompany().getParent()!=null) {//chỉ trụ sở mới parent==null.tránh chi nhánh thêm thiết bị GSHT
                    addActionError("action.error.permissionGSHT");
                    return INPUT;
                }
            }
            item.setUserCreated(user.getUsername());
            item.setUserUpdated(user.getUsername());
            item.setDateCreated(new Date());
            item.setDateUpdated(new Date());
            item.setCompanyId(user.getCompany().getId());
            beanFmsDao.addProductType(item);

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

    public ProductType getItem() {
        return item;
    }

    public void setItem(ProductType item) {
        this.item = item;
    }

    public List<Classify> getLstClassify(){
        List<Classify> lst=new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getCompany().getParent()==null){//chỉ trụ sở mới parent==null.tránh chi nhánh thêm thiết bị GSHT
            lst.add(new Classify(0, "GSHT"));
        }
        lst.add(new Classify(1, "SIM"));
        lst.add(new Classify(2,"Loại khác"));

        return lst;
    }
    public static class Classify{
        private int id;
        private String name;

        public Classify(int id,String name){
            this.id=id;
            this.name=name;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
