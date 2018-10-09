package com.eposi.fms.web.catalog.contract;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

public class CatalogContractAction extends AbstractAction {

    private static final long serialVersionUID = 90647710624183949L;
    private PagingResult page = new PagingResult();
    private String id;
    private int type;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_CONTRACT_TYPE_VIEW)) {
            return SUCCESS;
        }

        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }

            beanFmsDao.pageContractType(page);
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

}
