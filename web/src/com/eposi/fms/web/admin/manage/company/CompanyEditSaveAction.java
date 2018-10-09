package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import com.eposi.fms.v2.persitence.FmsV2Dao;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompanyEditSaveAction extends AbstractAction {
    private static final long serialVersionUID = 1788285538972807754L;
    private Company item;
    private List<District> districts=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        FmsV2Dao beanFmsV2Dao = (FmsV2Dao) this.getBean("beanFmsV2Dao");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_EDIT)){
            addActionError("action.error.permission");
            return ERROR;
        }


        try {
            if (item != null) {
                if(item.getCustomerType().getId()==null || item.getProvince()==null || item.getParent()==null || item.getParent().getId().equals("")){
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

                //Check company in db
                Company oldCompany = beanFmsDao.getCompany(item.getId());
                if(oldCompany!=null) {

                    if(oldCompany.isInforLocked()){//neu cong ty da khoa infor
                        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_DELETE)){
                            addActionError("action.error.permission");
                            return ERROR;
                        }
                    }
                    Company newCompany = mergeCompany(item, oldCompany);

                    //Update to DB v2
                    beanFmsV2Dao.editCompany(newCompany);

                    //Update V4
                    beanFmsDao.editCompany(newCompany);

                    //Update Province
                    if (!oldCompany.getProvince().getId().equals(item.getProvince().getId())) {
                        Province newProvince = beanFmsDao.getProvince(item.getProvince().getId());
                        Province oldProvince = oldCompany.getProvince();

                        newProvince.setCompanyCount(newProvince.getCompanyCount() + 1);
                        newProvince.setDriverCount(newProvince.getDriverCount() + oldCompany.getDriverCount());
                        newProvince.setVehicleCount(newProvince.getVehicleCount() + oldCompany.getVehicleCount());

                        oldProvince.setCompanyCount(oldProvince.getCompanyCount() - 1);
                        oldProvince.setDriverCount(oldProvince.getDriverCount() - oldCompany.getDriverCount());
                        oldProvince.setVehicleCount(oldProvince.getVehicleCount() - oldCompany.getVehicleCount());
                        beanFmsDao.saveProvince(oldProvince);
                        beanFmsDao.saveProvince(newProvince);
                    }

                    //Save Activity to Konexy
                    if(newCompany.getKonexyId()!=null) {
                        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
                        // Activity
                        Activity activity = new Activity();
                        activity.setId(newCompany.getId());
                        activity.setActionName("Update");
                        activity.setActorName(user.getUsername());
                        activity.setObjectId(newCompany.getId());
                        activity.setObjectName(Company.class.getName());
                        activity.setIndirectObjectName("/manage/company/detail.action?id=" + newCompany.getId());
                        activity.setDate(new Date());
                        activity.setContext("Cập nhật công ty");
                        activity.setPassive(true);

                        //Send message Activity to server Aggregate
                        byte[] bytes = conf.asByteArray(activity);
                        beanQueueingConsumerReportClient.put(bytes);
                    }
                    addActionMessage("Cập nhật tin doanh nghiệp thành công!!!");
                }
            } else {
                return INPUT;
            }
        } catch (Exception e) {
            this.addActionError(e.getMessage());
            return INPUT;
        }

        return SUCCESS;
	}

    private Company mergeCompany(Company item, Company newitem){
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(item!=null) {
            newitem.setName(item.getName());
            newitem.setCustomerType(item.getCustomerType());
            newitem.setPhone(item.getPhone());
            newitem.setEmail(item.getEmail());
            newitem.setUrl(item.getUrl());
            newitem.setProvince(item.getProvince());
            newitem.setParent(item.getParent());
            newitem.setNote(item.getNote());
            newitem.setDateUpdated(new Date());
            newitem.setUserUpdated(user.getUsername());
            newitem.setCode(item.getCode());

            Address address=newitem.getAddress();
            if(address==null) address=new Address();
            address.setProvince(item.getAddress().getProvince());
            address.setDistrict(item.getAddress().getDistrict());
            address.setCommune(item.getAddress().getCommune());
            address.setNo(item.getAddress().getNo());
            beanFmsDao.saveAddress(address);
            newitem.setAddress(address);

        }
        return newitem;
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
}
