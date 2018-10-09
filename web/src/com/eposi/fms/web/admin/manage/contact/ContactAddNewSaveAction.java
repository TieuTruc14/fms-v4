package com.eposi.fms.web.admin.manage.contact;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Activity;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Contact;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.apache.commons.lang.StringUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by TienManh on 7/22/2016.
 */
public class ContactAddNewSaveAction extends AbstractAction {

    private static final long serialVersionUID = -4043400815932935790L;
    private String companyId;
    private Contact item;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute(){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW)) return SUCCESS;
        try {
            if (item != null ) {
                if( StringUtils.isEmpty(item.getName())|| item.getPhone()==null || StringUtils.isEmpty(item.getPhone()) || companyId==null || StringUtils.isEmpty(companyId)){
                    this.addActionError("action.error.space");
                    return INPUT;
                }
                Contact exstingItem = beanFmsDao.getContact(item.getId());
                if (exstingItem != null) {
                    this.addActionError("action.error.dupplicate");
                    return INPUT;
                }
                Company company=beanFmsDao.getCompany(companyId);
                if(company!=null){
                    item.setCompany(company);
                    item.setDateCreated(new Date());
                    item.setUserCreated(user.getUsername());
                    item.setDateUpdated(new Date());
                    item.setUserUpdated(user.getUsername());
                    beanFmsDao.addContact(item);

                    // Activity
                    Activity activity = new Activity();
                    activity.setId(company.getId());
                    activity.setActionName("Addnew");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(item.getName());
                    activity.setObjectName(Contact.class.getName());
                    activity.setIndirectObjectName("/manage/contact/list.action?vehicleId=" + item.getName());
                    activity.setDate(new Date());
                    activity.setContext("Thêm mới hợp đồng");
                    activity.setPassive(true);
                    //Send message Activity to server Aggregate
                    byte[] bytes = conf.asByteArray(activity);
                    beanQueueingConsumerReportClient.put(bytes);
                }

            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Contact getItem() {
        return item;
    }

    public void setItem(Contact item) {
        this.item = item;
    }
}
