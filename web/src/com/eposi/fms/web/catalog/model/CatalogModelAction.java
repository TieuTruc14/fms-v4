package com.eposi.fms.web.catalog.model;

import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Model;
import com.eposi.fms.model.ProductType;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.PagingResult;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class CatalogModelAction extends AbstractAction {

    private static final long serialVersionUID = 90647710624183949L;
    private PagingResult page = new PagingResult();
    private String id;
    private int type;

	public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_MODEL_VIEW)) return SUCCESS;
        try {
            if (page.getPageNumber() > 0){
                page.setPageNumber(page.getPageNumber() -1 );
            }
            beanFmsDao.pageModel(page);

            //chi tru so dc xem all, con lai remove GSHT
            if(user.getCompany().getParent()!=null){
                List<?> items= page.getItems();
                for(int i=items.size()-1;i>=0;i--){
                    Model model=(Model)items.get(i);
                    if(model.getProductType().getType()==0){
                        items.remove(i);
                    }
                }
                page.setItems(items);
            }
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.manage.VehicleListingAction.excute: "+e.getMessage());
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
