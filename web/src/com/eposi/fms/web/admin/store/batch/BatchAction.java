package com.eposi.fms.web.admin.store.batch;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Batch;
import com.eposi.fms.model.Model;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class BatchAction extends AbstractAction {
    private static final long serialVersionUID = 90647710624183949L;
    private PagingResult page = new PagingResult();
    private String  id;
    private String name;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_VIEW)){
            addActionError("action.error.permission");
            return INPUT;
        }

        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }
            beanFmsDao.pageBatch(page, user.getCompany(), id,name);
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.Batch.excute: "+e.getMessage());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
