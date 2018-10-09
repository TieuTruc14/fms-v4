package com.eposi.fms.web.admin.manage.organization;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class OrgazationDetailAction extends AbstractAction {
    private static final long serialVersionUID = 3381599048208459573L;

    private String id;
    private Company item;
    private List<Company> items;
    private PagingResult page = new PagingResult();
    private List<District> districts=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();
    private int indexCount = 0;


	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_ORG_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }


        try {
            if (StringUtils.isNotEmpty(id)){
                item = beanFmsDao.getCompany(id);
                if(!beanFmsDao.isParent(item, user.getCompany())){
                    addActionError("action.error.permission");
                    return ERROR;
                }

                if(item!=null){
                    items = beanFmsDao.getAllCompanyByParent(item);
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

                    //khi edit can load thong tin
                    if(item.getAddress()!=null){
                        districts=beanFmsDao.searchDistrictByProvince(item.getAddress().getProvince());
                        communes=beanFmsDao.searchCommuneByDistrict(item.getAddress().getDistrict());
                    }
                }
            }
        }catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.company.OrgazationDetailAction.details: " + e.getMessage());
        }

		return SUCCESS;
	}

    public List<Vehicle> getVehicles() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");

        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        if (items != null) {
            vehicles = beanFmsDao.searchVehicleInCompanies(items);
        }

        return vehicles;
    }

    public List<User> getUsers() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        List<User> users = new ArrayList<User>();
        if(item!=null) {
            users = (List<User>) beanFmsDao.searchUserByCompany(item);
        }
        return users;
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

    public Company getItem() {
        return item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Company> getItems() {
        return items;
    }

    public void setItems(List<Company> items) {
        this.items = items;
    }

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
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

    public int getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
    }
}
