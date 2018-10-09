package com.eposi.fms.web.admin.manage.organization;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import com.eposi.fms.v2.model.Owner;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.apache.commons.lang.StringUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrgazationAddNewSaveAction extends AbstractAction {
    private static final long serialVersionUID = -2082664241272796469L;

    private Company item;
    private List<District> districts=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();
    private String companyParentId;
    private Company companyParent;
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_ORG_ADD)){
            addActionError("action.error.permission");
            return ERROR;
        }

        try {
            if (item != null) {
                if(item.getCustomerType().getId()==null || item.getCustomerType().getId().equals("") || item.getName()==null || item.getName().equals("") || item.getCode()==null||item.getCode().equals("")
                        || item.getPhone()==null || item.getPhone().equals("")){
                    addActionError("action.error.space");
                    return INPUT;
                }
                if(companyParentId!=null && StringUtils.isNotEmpty(companyParentId)){
                    companyParent=beanFmsDao.getCompany(companyParentId);
                    item.setParent(companyParent);
                }else if(item.getParent()==null || item.getParent().getId().equals("")){
                    addActionError("action.error.space");
                    return INPUT;
                }
                if( item.getAddress().getCommune()==null || item.getAddress().getCommune().getId().equals("")||item.getAddress().getDistrict()==null||item.getAddress().getDistrict().getId().equals("") ||
                        item.getAddress().getNo()==null ||item.getAddress().getNo().equals("")|| item.getAddress().getProvince().getId()==null ||item.getAddress().getProvince().getId().equals("")){
                    addActionError("action.error.space");
                    return INPUT;
                }
                /*phuc vu load lai dia chi khi return input*/
                Province provinceAddress=beanFmsDao.getProvince(item.getAddress().getProvince().getId());
                District district=beanFmsDao.getDistrict(item.getAddress().getDistrict().getId());
                if(provinceAddress==null || district==null){
                    addActionError("action.error.space");
                    return INPUT;
                }
                districts=beanFmsDao.searchDistrictByProvince(provinceAddress);
                communes=beanFmsDao.searchCommuneByDistrict(district);

                String companyId= beanFmsDao.GeneratorCompanyId(item);
                if(companyId==null||companyId.isEmpty()){
                    addActionError("action.error.space");
                    return INPUT;
                }
                item.setId(companyId);
                item.setDateCreated(new Date());
                item.setUserCreated(user.getUsername());
                item.setOrgazation(true);

                if( item.getOwner()>0){
                    Owner owner=beanFmsV2Dao.getOwner(item.getOwner());
                    if(owner==null){
                        addActionError("action.error.exits");
                        return INPUT;
                    }
                    if(beanFmsDao.getCompanyByOwner(owner.getId())!=null){
                        addActionError("action.error.duplicate");
                        return INPUT;
                    }
                    //Create KonexyID for logging activity
                    String description = "{ \"name\":\"" + item.getId() + "\"}";
                    String konexyId = beanReportReader.newKonexyId(description);
                    item.setKonexyId(konexyId);

                    Address address=item.getAddress();
                    beanFmsDao.addAddress(address);

                    beanFmsDao.addCompany(item);
                }else{
                    //Create KonexyID for logging activity
                    String description = "{ \"name\":\"" + item.getId() + "\"}";
                    String konexyId = beanReportReader.newKonexyId(description);
                    item.setKonexyId(konexyId);
                    //Save to DB V2
                    Owner owner = beanFmsV2Dao.addOrganization(item);
                    //Update Owner to V4
                    item.setOwner(owner.getId());
                    Address address=item.getAddress();
                    beanFmsDao.addAddress(address);
                    beanFmsDao.addCompany(item);
                }

                addActionMessage("Thêm thông tin doanh nghiệp thành công!!!");

                if(item.getKonexyId()!=null) {
                    // Activity
                    Activity activity = new Activity();
                    activity.setId(companyId);
                    activity.setActionName("Addnew");
                    activity.setActorName(user.getUsername());
                    activity.setObjectId(item.getId());
                    activity.setObjectName(Company.class.getName());
                    activity.setIndirectObjectName("/manage/org/detail.action?id=" + item.getId());
                    activity.setDate(new Date());
                    activity.setContext("Thêm mới công ty");
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

	@Override
	public void validate() {
		super.validate();
	}

    public String getEditMode() {
        return "addnew.save";
    }

	public Company getItem() {
		return item;
	}


    public void setItem(Company item) {
		this.item = item;
	}

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public String getCompanyParentId() {
        return companyParentId;
    }

    public void setCompanyParentId(String companyParentId) {
        this.companyParentId = companyParentId;
    }

    public Company getCompanyParent() {
        return companyParent;
    }

    public void setCompanyParent(Company companyParent) {
        this.companyParent = companyParent;
    }
}
