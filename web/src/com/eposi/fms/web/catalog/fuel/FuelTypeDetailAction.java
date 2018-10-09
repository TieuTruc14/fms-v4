package com.eposi.fms.web.catalog.fuel;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.FuelType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class FuelTypeDetailAction extends AbstractAction {
    private static final long serialVersionUID = 278764420622126480L;

    private String id;
    private FuelType item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_FUEL_VIEW)) return SUCCESS;
        try {
            if (StringUtils.isNotEmpty(id)){
                int fuelId = Integer.parseInt(id);
                item = beanFmsDao.getFuelType(fuelId);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.catalog.InsuranceDetailAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FuelType getItem() {
        return item;
    }

    public void setItem(FuelType item) {
        this.item = item;
    }

}
