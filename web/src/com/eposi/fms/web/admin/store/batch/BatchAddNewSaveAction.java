package com.eposi.fms.web.admin.store.batch;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class BatchAddNewSaveAction extends AbstractAction {

    private static final long serialVersionUID = 9112583072760501169L;
    private Batch item;
    private String strDateStart;
    private String strDateEnd;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_BATCH_ADD)){
            addActionError("action.error.permission");
            return INPUT;
        }

        if (item == null) {
            return SUCCESS;
        }
        if(item.getModel()==null || item.getModel().getId().equals("")){
            return INPUT;
        }
        if (strDateStart == null || strDateStart.isEmpty() ) {
            return INPUT;
        }
        if (strDateEnd == null || strDateEnd.isEmpty()) {
            return INPUT;
        }

        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = FormatUtil.parseDate(strDateStart, "dd/MM/yyyy");
            item.setDateStart(dateStart);
            dateEnd = FormatUtil.parseDate(strDateEnd, "dd/MM/yyyy");
            item.setDateEnd(dateEnd);
            Model model = beanFmsDao.getModel(item.getModel().getId());
            if (model == null) {
                return INPUT;
            }
            String code = beanFmsDao.GeneratorCodeNewBatch(model);
            if (code == null) {
                return INPUT;
            }

            item.setCode(code);

            item.setDateCreated(new Date());
            if(user !=null){
                item.setUserCreated(user.getUsername());
            }
            String id = model.getId()+code;
            item.setId(id);
            item.setCompanyId(user.getCompany().getId());
            String description = "{ \"name\":\"" + item.getId() + "\"}";
            String konexyId = beanReportReader.newKonexyId(description);
            item.setKonexyId(konexyId);

            beanFmsDao.saveBatch(item);
            // Activity
            Activity activity = new Activity();
            activity.setId(item.getCompanyId());
            activity.setActionName("Addnew");
            activity.setActorName(user.getUsername());
            activity.setObjectId(item.getId());
            activity.setObjectName(Batch.class.getName());
            activity.setIndirectObjectName("/store/batch/batch.detail.action?id" + item.getId());
            activity.setDate(new Date());
            activity.setContext("Thêm mới lô");
            activity.setPassive(true);
            //Send message Activity to server Aggregate
            byte[] bytes = conf.asByteArray(activity);
            beanQueueingConsumerReportClient.put(bytes);

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

    public String getStrDateStart() {
        return strDateStart;
    }

    public void setStrDateStart(String strDateStart) {
        this.strDateStart = strDateStart;
    }

    public Batch getItem() {
        return item;
    }

    public void setItem(Batch item) {
        this.item = item;
    }

    public String getStrDateEnd() {
        return strDateEnd;
    }

    public void setStrDateEnd(String strDateEnd) {
        this.strDateEnd = strDateEnd;
    }
}
