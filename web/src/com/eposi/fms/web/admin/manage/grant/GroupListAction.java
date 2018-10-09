package com.eposi.fms.web.admin.manage.grant;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 7/15/2016.
 */
public class GroupListAction extends AbstractAction{
    private static final long serialVersionUID = -334879278794273731L;

    private PagingResult page = new PagingResult();

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_GROUP_VIEW)) {
            return SUCCESS;
        }
        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }

            beanFmsDao.pageGroup(page);
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.catolog.VehicleListingAction.excute: "+e.getMessage());
        }

        return SUCCESS;
    }

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }
}
