package com.eposi.fms.web.admin.store.batch;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Batch;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by TienManh on 6/10/2016.
 */
public class BatchEditAction extends AbstractAction {

    private static final long serialVersionUID = 1138264262016817536L;
    private String id;
    private String strDateStart;
    private String strDateEnd;
    private Batch item;
    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_EDIT)){
                addActionError("action.error.permission");
                return INPUT;
            }

            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getBatch(id);
                if(item.getDateStart()!=null) {
                    setStrDateStart(FormatUtil.formatDate(item.getDateStart(), "dd/MM/yyyy"));
                }
                if(item.getDateEnd()!=null) {
                    setStrDateEnd(FormatUtil.formatDate(item.getDateEnd(), "dd/MM/yyyy"));
                }
            }

        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.BatchAction: "+ e.getMessage());
        }

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public String getStrDateEnd() {
        return strDateEnd;
    }

    public void setStrDateEnd(String strDateEnd) {
        this.strDateEnd = strDateEnd;
    }

    public Batch getItem() {
        return item;
    }

    public void setItem(Batch item) {
        this.item = item;
    }
}
