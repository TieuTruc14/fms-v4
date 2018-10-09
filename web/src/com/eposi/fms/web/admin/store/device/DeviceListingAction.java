package com.eposi.fms.web.admin.store.device;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

public class DeviceListingAction extends AbstractAction {
    private static final long serialVersionUID = 90647710624183949L;
    private PagingResult page = new PagingResult();
    private String  id;
    private int type;
    private String productTypeId;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_VIEW)){
                addActionError("action.error.permission");
                return ERROR;
            }
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }
            if(type==0){
                beanFmsDao.pageDevice(page, id, 10, user.getCompany(),productTypeId);
            }else if(type==1){
                beanFmsDao.pageDevice(page, id, 1, user.getCompany(),productTypeId);
            }else if(type==2){
                beanFmsDao.pageDevice(page, id, 0, user.getCompany(),productTypeId);
            }else if(type==3){
                beanFmsDao.pageDevice(page, id, 3, user.getCompany(),productTypeId);
            }
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.VehicleListingAction.excute: "+e.getMessage());
        }
        return SUCCESS;
	}

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }
}
