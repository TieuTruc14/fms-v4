package com.eposi.fms.web.admin.store.batch;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Batch;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;


public class BatchDetailAction extends AbstractAction {

    private static final long serialVersionUID = 8940121396650885291L;
    private String id;
    private Batch item;
    private PagingResult page = new PagingResult();
    private String message="";

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_VIEW)){
            addActionError("action.error.permission");
            return INPUT;
        }

        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getBatch(id);
                if (page.getPageNumber() > 0){
                    page.setPageNumber(page.getPageNumber() -1 );
                }
                beanFmsDao.pageDeviceByBatch(page,item);
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.BatchDetailAction: "+ e.getMessage());
        }

        return SUCCESS;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Batch getItem() {
        return item;
    }

    public void setItem(Batch item) {
        this.item = item;
    }

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
