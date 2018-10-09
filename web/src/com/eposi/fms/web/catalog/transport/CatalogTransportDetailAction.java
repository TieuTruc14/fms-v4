package com.eposi.fms.web.catalog.transport;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.TransportType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class CatalogTransportDetailAction extends AbstractAction {
    private static final long serialVersionUID = 278764420622126480L;

    private String id;
    private TransportType item;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_TRANSPORT_VIEW)) return SUCCESS;
        try {
            if (StringUtils.isNotEmpty(id)){
                int contractTypeId = Integer.parseInt(id);
                item = beanFmsDao.getTransportType(contractTypeId);
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

    public TransportType getItem() {
        return item;
    }

    public void setItem(TransportType item) {
        this.item = item;
    }
}
