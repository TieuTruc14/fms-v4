package com.eposi.fms.web.admin.manage.company;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class CompanyDetailAction extends AbstractAction {
    private static final long serialVersionUID = 3381599048208459573L;

    private String id;
    private Company item;
    private List<Company> items;
    private PagingResult page = new PagingResult();
    private int indexCount = 0;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_COMPANY_VIEW) || StringUtils.isEmpty(id)){
            addActionError("action.error.permission");
            return ERROR;
        }

        item  = beanFmsDao.getCompany(id);
        if(item ==null){
            addActionError("action.error.permission");
            return ERROR;
        }

        if(!beanFmsDao.isParent(item, user.getCompany())){
            addActionError("action.error.permission");
            return ERROR;
        }


        try {
            if (StringUtils.isNotEmpty(id)){
                items = beanFmsDao.searchCompanyByParent(item);
                int firstIdx =0;
                if(page.getNumberPerPage()>0) {
                    firstIdx = (page.getPageNumber() - 1) * 50;
                }
                int endIdx   = page.getPageNumber()*50;
                page.setNumberPerPage(50);

                if(items!=null){
                    if(items.size()>0){
                        List<Company> pageItems = getPageItem(items, firstIdx, endIdx);
                        if(pageItems.size()>0){
                            page.setRowCount(items.size());
                            page.setItems(pageItems);
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.company.CompanyDetailAction.details: " + e.getMessage());
        }
		return SUCCESS;
	}

    public List<Vehicle> getVehicles() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        return beanFmsDao.searchVehicleByCompany(item);
    }


    public List<Driver> getDrivers() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        return beanFmsDao.searchDriverByCompany(item);
    }

    public List<Contact> getContacts() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        return beanFmsDao.searchContactByCompany(item);
    }

    public List<User> getUsers() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        return beanFmsDao.searchUserByCompany(item);
    }

    private List<Company> getPageItem(List<Company> items, int firstIdx, int endIdx){
        List<Company> result = new ArrayList<>();
        if(endIdx>items.size()){
            endIdx = items.size()-1;
        }
        if(firstIdx>items.size()){
            firstIdx = items.size()-1;
        }

        setIndexCount(firstIdx);

        for(int i=firstIdx;i<endIdx;i++){
            result.add(items.get(i));
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company getItem() {
        return item;
    }

    public void setItem(Company item) {
        this.item = item;
    }

    public List<Company> getItems() {
        return items;
    }

    public void setItems(List<Company> items) {
        this.items = items;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
    }

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }
}
