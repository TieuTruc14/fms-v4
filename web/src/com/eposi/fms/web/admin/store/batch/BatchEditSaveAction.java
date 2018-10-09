package com.eposi.fms.web.admin.store.batch;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Batch;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class BatchEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 8650582305269574888L;

    private Batch item;
    private String strDateStart;
    private String strDateEnd;
    private String lock="";
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_EDIT)){
            addActionError("action.error.permission");
            return INPUT;
        }

        if(lock.equals("lock")){
            if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_ADD)){
                addActionError("action.error.permission");
                return INPUT;
            }

            item=beanFmsDao.getBatch(item.getId());
            try {

                if(user!=null && item!=null){
                    item.setLocked(true);
                    beanFmsDao.editBatch(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return SUCCESS;
        }
        if (item == null ) {
            return SUCCESS;
        }
        if (strDateEnd == null || strDateEnd.isEmpty() || strDateStart == null || strDateStart.isEmpty()) {
            return SUCCESS;
        }

        Batch batch=beanFmsDao.getBatch(item.getId());
        if(batch==null){
            return  SUCCESS;
        }

        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart   = FormatUtil.parseDate(strDateStart, "dd/MM/yyyy");
            dateEnd     = FormatUtil.parseDate(strDateEnd, "dd/MM/yyyy");
            if(dateStart.after(dateEnd)) return SUCCESS;

            if(user!=null){
                batch.setDateStart(dateStart);
                batch.setDateEnd(dateEnd);
                batch.setDateUpdated(new Date());
                batch.setUserUpdated(user.getUsername());
                batch.setName(item.getName());
                batch.setDescription(item.getDescription());
                beanFmsDao.editBatch(batch);

                // Activity
                Activity activity = new Activity();
                activity.setId(item.getCompanyId());
                activity.setActionName("Update");
                activity.setActorName(user.getUsername());
                activity.setObjectId(item.getId());
                activity.setObjectName(Batch.class.getName());
                activity.setIndirectObjectName("/store/batch/batch.detail.action?id" + item.getId());
                activity.setDate(new Date());
                activity.setContext("Update lô");
                activity.setPassive(true);
                //Send message Activity to server Aggregate
                byte[] bytes = conf.asByteArray(activity);
                beanQueueingConsumerReportClient.put(bytes);
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

    public Batch getItem() {
        return item;
    }

    public void setItem(Batch item) {
        this.item = item;
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

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }
}
